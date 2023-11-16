package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.entidades.Cliente;
import com.EquipoB.AsadoYPileta.entidades.Contacto;
import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.Propietario;
import com.EquipoB.AsadoYPileta.entidades.Reserva;
import com.EquipoB.AsadoYPileta.entidades.Usuario;
import com.EquipoB.AsadoYPileta.enumeraciones.Rol;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.excepciones.PermisosException;
import com.EquipoB.AsadoYPileta.servicios.ClienteServicio;
import com.EquipoB.AsadoYPileta.servicios.PropiedadServicio;
import com.EquipoB.AsadoYPileta.servicios.PropietarioServicio;
import com.EquipoB.AsadoYPileta.servicios.ReservaServicio;
import com.EquipoB.AsadoYPileta.servicios.TipoContactoServicio;
import com.EquipoB.AsadoYPileta.servicios.UsuarioServicio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/cliente")
public class ClienteControlador {

    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private TipoContactoServicio tipoContactoServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private PropietarioServicio propietarioServicio;
    
    @Autowired
    private ReservaServicio reservaServicio;


    private Rol rol;

    @GetMapping("/registrar")
    public ModelAndView registrar(ModelMap modelo) {
        modelo.addAttribute("tipoContacto", tipoContactoServicio.listarTipoContacto());
        return new ModelAndView("registro_usuario.html", modelo);
    }

    @PostMapping("/registro")
    public RedirectView registro(@RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String password, @RequestParam String password2,
            @RequestParam MultipartFile[] imagenesInput, @RequestParam String descripcion,
            @RequestParam String[] tipoContactoInput, @RequestParam String[] contactosInput, @RequestParam String rol) throws Exception {

        clienteServicio.crearCliente(email, nombre, apellido, descripcion,
                password, password2, imagenesInput, tipoContactoInput, contactosInput, rol);

        return new RedirectView("/login");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROPIETARIO','ROLE_CLIENTE')")
    @GetMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable String id, HttpSession session) throws MiException, PermisosException {
        usuarioServicio.eliminarUsuario(id, session);
        return new RedirectView("/");
    }

    @GetMapping("/perfil")
    public ModelAndView perfil(ModelMap modelo, HttpSession session) throws MiException {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        if (usuario.getRol().equals(Rol.CLIENTE)) {
            Cliente cliente = clienteServicio.getOne(usuario.getId());
            List<Reserva> reservasCliente = reservaServicio.listarReservaCliente(usuario.getId());
            modelo.put("cliente", cliente);
            modelo.addAttribute("reservas",reservasCliente);
            modelo.addAttribute("tipoContacto", tipoContactoServicio.listarTipoContactoUsuario(cliente));
        } else if (usuario.getRol().equals(Rol.PROPIETARIO)) {
            Propietario propietario = new Propietario();
            Optional<Propietario> respuesta = propietarioServicio.getOne(usuario.getId());
            if(respuesta.isPresent()){
                propietario = respuesta.get();                
            } else {
                propietario = propietarioServicio.crearPropietario(usuario);
            }
            modelo.put("cliente", propietario.getCliente());
            modelo.put("propiedades", propietario.getPropiedades());
            modelo.addAttribute("tipoContacto", tipoContactoServicio.listarTipoContactoUsuario(propietario.getCliente()));
        }
        return new ModelAndView("perfil_usuario.html", modelo);
    }

    @PostMapping("/perfil/{id}")
    public RedirectView modificar(@RequestParam String nombre, @PathVariable String id, @RequestParam String apellido,
            @RequestParam String email, @RequestParam MultipartFile[] imagenesInput,
            @RequestParam String descripcion, @RequestParam String[] tipoContactoInput,
            @RequestParam String[] contactosInput, @RequestParam(required = false) String[] imagenesViejas) throws Exception { 
            clienteServicio.modificarCliente(email, id, nombre, apellido, descripcion,
                    imagenesInput, tipoContactoInput, contactosInput, imagenesViejas);   
        return new RedirectView("/");
    }

    @PostMapping("/cambiar_password")
    public ModelAndView cambiandoPassword(HttpSession session, @RequestParam String password, 
            @RequestParam String passwordNuevo, @RequestParam String passwordNuevo2, ModelMap modelo) throws MiException {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        try {
            usuarioServicio.cambiarPassword(usuario, password, passwordNuevo, passwordNuevo2);           
            return new ModelAndView("redirect:/cliente/perfil", modelo);
        } catch (MiException e) {
            modelo.addAttribute("error", e.getMessage());
            return new ModelAndView("redirect:/cliente/perfil", modelo);            
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/baja")
    public ModelAndView bajaUsuario(@RequestParam String idUsuario, ModelMap modelo) throws MiException {
        try {
            usuarioServicio.bajaUsuario(idUsuario);
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return new ModelAndView("redirect:/admin/dashboard", modelo);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/alta")
    public ModelAndView altaUsuario(@RequestParam String idUsuario, ModelMap modelo) throws MiException {
        try {
            usuarioServicio.altaUsuario(idUsuario);
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return new ModelAndView("redirect:/admin/dashboard", modelo);
    }

}
