package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.entidades.Cliente;
import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.Reserva;
import com.EquipoB.AsadoYPileta.entidades.Servicio;
import com.EquipoB.AsadoYPileta.entidades.Usuario;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.servicios.ClienteServicio;
import com.EquipoB.AsadoYPileta.servicios.PropiedadServicio;
import com.EquipoB.AsadoYPileta.servicios.ReservaServicio;
import com.EquipoB.AsadoYPileta.servicios.ServicioServicio;
import com.EquipoB.AsadoYPileta.servicios.UsuarioServicio;
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
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private ServicioServicio servicioServicio;

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_PROPIETARIO')")
    @PostMapping("/registrar") 
    public ModelAndView crearReserva(@RequestParam String idPropiedad, @RequestParam String fechaInicio,
            @RequestParam String fechaFinal, HttpSession session, ModelMap modelo) throws ParseException, MiException {
        Reserva reserva = new Reserva();
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        Cliente cliente = clienteServicio.getOne(usuario.getId());
        Propiedad propiedad = propiedadServicio.getOne(idPropiedad);   
        List<Servicio> servicios = servicioServicio.listarServicios();
        
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");     
        reserva.setFechaInicio(formato.parse(fechaInicio));
        reserva.setFechaFin(formato.parse(fechaFinal));
        propiedad.getFiltroDisponibilidad().habilitado(reserva.getFechaInicio(), reserva.getFechaFin());
        
        modelo.addAttribute("servicios", servicios);
        modelo.addAttribute("propiedad", propiedad);
        modelo.addAttribute("cliente", cliente);
        modelo.addAttribute("reserva", reserva);
        
        return new ModelAndView("confirmacion_reserva.html", modelo);
    }

    @GetMapping("/listar")  //localhost:8080/reserva/listar

    public String listarReservas(ModelMap modelo) {
        List<Reserva> reservas = reservaServicio.listarReserva();
        modelo.addAttribute("reservas", reservas);

        return "reserva_lista.html";
    }

    @PostMapping("/registro")
    public String registroReserva(String id, String mensaje, Date fechaInicio, Date fechaFin, List serviciosElegidas, Double montoTotal, Boolean disponible, ModelMap modelo) {

        try {
            reservaServicio.crearReserva(mensaje, fechaInicio, fechaFin, serviciosElegidas, montoTotal, disponible);
            return "redirect:/reserva/listar";
        } catch (MiException e) {

            modelo.addAttribute("error", e.getMessage());

            return "reserva.html";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificarReserva(@PathVariable String id, ModelMap modelo) {

        modelo.put("reserva", reservaServicio.getOne(id));

        return "reserva_modificar.html";

    }

    @PostMapping("/modificar/{id}")
    public String modificarReserva(@PathVariable String id, String mensaje, Date fechaInicio, Date fechaFin, List serviciosElegidas, Double montoTotal, Boolean disponible, ModelMap modelo) {

        try {
            reservaServicio.modificarReserva(id, mensaje, fechaInicio, fechaFin, serviciosElegidas, montoTotal, disponible);

            return "redirect:/reserva/listar";
        } catch (MiException e) {

            modelo.addAttribute("error", e.getMessage());

            return "reserva_modificar";
        }

    }

    @GetMapping("/borrar/{id}")
    public String borrarReserva(@PathVariable String id) {

        reservaServicio.borrar(id);

        return "redirect:/reserva/listar";

    }
    
    @PostMapping("/aceptarReserva/{id}")
    public String aceptar(@PathVariable String id) throws MiException{
        Reserva reserva = reservaServicio.getOne(id);
        reserva.setDisponible(true);
        reservaServicio.modificarReserva(id, reserva.getMensaje(), 
                reserva.getFechaInicio(), reserva.getFechaFin(), 
                reserva.getServiciosElegidas(), reserva.getMontoTotal(), 
                reserva.getDisponible());
        return "redirect:/cliente/perfil";        
    }    

}

/*
@RequestParam String[] idServicio,
List<Servicio> serviciosElegidos = new ArrayList<>();
        for (String Servicio : idServicio) {
            serviciosElegidos.add(servicioServicio.getOne(Servicio));
            montoTotal = montoTotal + servicioServicio.getOne(Servicio).getValor()*montoPropiedad;
        }

        reserva.setServiciosElegidas(serviciosElegidos);
*/