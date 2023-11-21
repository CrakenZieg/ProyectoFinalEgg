package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.entidades.Cliente;
import com.EquipoB.AsadoYPileta.entidades.Contacto;
import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.Reserva;
import com.EquipoB.AsadoYPileta.entidades.Servicio;
import com.EquipoB.AsadoYPileta.entidades.Usuario;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.excepciones.PermisosException;
import com.EquipoB.AsadoYPileta.servicios.ClienteServicio;
import com.EquipoB.AsadoYPileta.servicios.PropiedadServicio;
import com.EquipoB.AsadoYPileta.servicios.ReservaServicio;
import com.EquipoB.AsadoYPileta.servicios.ServicioServicio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/reserva") //localhost:8080/reserva
public class ReservaControlador {

    @Autowired
    private ReservaServicio reservaServicio;
    @Autowired
    private PropiedadServicio propiedadServicio;
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private ServicioServicio servicioServicio;

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_PROPIETARIO')")
    @PostMapping("/registrar")
    public ModelAndView crearReserva(@RequestParam String idPropiedad, @RequestParam String fechaInicio,
            @RequestParam String fechaFinal, HttpSession session, ModelMap modelo) throws ParseException, MiException {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        Cliente cliente = clienteServicio.getOne(usuario.getId());
        Propiedad propiedad = propiedadServicio.getOne(idPropiedad);
        List<Servicio> servicios = servicioServicio.listarServicios();

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        propiedad.getFiltroDisponibilidad().habilitado(formato.parse(fechaInicio), formato.parse(fechaFinal));

        modelo.addAttribute("servicios", servicios);
        modelo.addAttribute("propiedad", propiedad);
        modelo.addAttribute("cliente", cliente);
        modelo.addAttribute("fechaInicio", fechaInicio);
        modelo.addAttribute("fechaFinal", fechaFinal);

        return new ModelAndView("confirmacion_reserva.html", modelo);
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_PROPIETARIO')")
    @PostMapping("/registro")
    public ModelAndView registroReserva(String idPropiedad, String idCliente, String mensaje, String fechaInicio, String fechaFin, String[] serviciosElegidas, ModelMap modelo, Usuario logueado) throws MiException, ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        reservaServicio.crearReserva(idPropiedad, idCliente, mensaje, formato.parse(fechaInicio), formato.parse(fechaFin),
                servicioServicio.listarServiciosArray(serviciosElegidas), logueado);
        return new ModelAndView("redirect:/cliente/perfil", modelo);
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_PROPIETARIO')")
    @GetMapping("/modificar/{id}")
    public ModelAndView modificarReserva(@PathVariable String id, ModelMap modelo) {
        modelo.put("reserva", reservaServicio.getOne(id));
        return new ModelAndView("reserva_modificar.html", modelo);
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_PROPIETARIO')")
    @GetMapping("/ver/{id}")
    public ModelAndView verReserva(@PathVariable String id, HttpSession session, ModelMap modelo) throws PermisosException {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        Reserva reserva = reservaServicio.getOne(id);
        if (!reserva.getCliente().getId().equals(usuario.getId()) && !reserva.getPropiedad().getIdPropietario().equals(usuario.getId())) {
            throw new PermisosException("No tienes permiso para acceder a estos datos");
        }
        List<Contacto> contactosPropietario = clienteServicio.getOne(reserva.getPropiedad().getIdPropietario()).getContactos();
        List<Contacto> contactosCliente = reserva.getCliente().getContactos();
        modelo.addAttribute("contactosPropietario", contactosPropietario);
        modelo.addAttribute("contactosCliente", contactosCliente);
        modelo.put("reserva", reserva);
        return new ModelAndView("reserva.html", modelo);
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_PROPIETARIO')")
    @PostMapping("/modificar/{id}")
    public String modificarReserva(@PathVariable String id, String mensaje, Date fechaInicio, Date fechaFin, List serviciosElegidas, ModelMap modelo,
            Usuario logueado) {
        try {
            reservaServicio.modificarReserva(id, mensaje, fechaInicio, fechaFin, serviciosElegidas, logueado);
            return "redirect:/reserva/listar";
        } catch (MiException e) {
            modelo.addAttribute("error", e.getMessage());
            return "reserva_modificar";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_PROPIETARIO')")
    @GetMapping("/borrar/{id}")
    public ModelAndView borrarReserva(@PathVariable String id, HttpSession session) throws PermisosException {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        Reserva reserva = reservaServicio.getOne(id);
        if (!reserva.getPropiedad().getIdPropietario().equals(usuario.getId()) && !reserva.getCliente().getId().equals(usuario.getId())) {
            throw new PermisosException("No es posible borrar una reserva de una propiedad ajena");
        }
        reservaServicio.borrar(id);
        return new ModelAndView("redirect:/cliente/perfil");
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_PROPIETARIO')")
    @GetMapping("/aceptarReserva/{id}")
    public ModelAndView aceptar(@PathVariable String id, HttpSession session) throws PermisosException {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        if (!reservaServicio.getOne(id).getPropiedad().getIdPropietario().equals(usuario.getId())) {
            throw new PermisosException("No es posible aceptar una reserva de una propiedad ajena");
        }
        reservaServicio.aceptarReserva(id);
        return new ModelAndView("redirect:/cliente/perfil");
    }

}
