package com.EquipoB.AsadoYPileta.controladores;


import com.EquipoB.AsadoYPileta.entidades.Cliente;
import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.Propietario;
import com.EquipoB.AsadoYPileta.entidades.Usuario;
import com.EquipoB.AsadoYPileta.enumeraciones.Rol;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.excepciones.PermisosException;
import com.EquipoB.AsadoYPileta.servicios.ClienteServicio;
import com.EquipoB.AsadoYPileta.servicios.PropiedadServicio;
import com.EquipoB.AsadoYPileta.servicios.PropietarioServicio;
import com.EquipoB.AsadoYPileta.servicios.TipoContactoServicio;
import com.EquipoB.AsadoYPileta.servicios.UsuarioServicio;
import java.util.ArrayList;
import java.util.List;
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

    private Rol rol;


    @GetMapping("/registrar")
    public String registrar(ModelMap model) {
        model.addAttribute("tipoContacto", tipoContactoServicio.listarTipoContacto());
        return "registro_usuario.html";
    }


    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String password, @RequestParam String password2,
            @RequestParam MultipartFile[] imagenesInput, @RequestParam String descripcion,
            @RequestParam String[] tipoContactoInput, @RequestParam String[] contactosInput) {
        try {
            clienteServicio.crearCliente(email, nombre, apellido, descripcion,
                    password, password2, imagenesInput, tipoContactoInput, contactosInput);
        } catch (Exception ex) {
            Logger.getLogger(ClienteControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROPIETARIO','ROLE_CLIENTE')")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, HttpSession session) throws MiException, PermisosException {
        usuarioServicio.eliminarUsuario(id, session);
        return "index.html";
    }


    @GetMapping("/perfil")
    public String perfil(ModelMap model, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        
        
        if (usuario.getRol().equals(Rol.CLIENTE)) {
            Cliente cliente = clienteServicio.getOne(usuario.getId());
            model.put("cliente", cliente);
            model.addAttribute("tipoContacto", tipoContactoServicio.listarTipoContactoUsuario(cliente));
        } else if (usuario.getRol().equals(Rol.PROPIETARIO) || usuario.getRol().equals(Rol.ADMIN)) {
            Propietario propietario;
            try {
                propietario = propietarioServicio.getOne(usuario.getId());
                model.put("cliente", propietario.getCliente());
                model.put("propiedades", propietario.getPropiedades());
                model.addAttribute("tipoContacto", tipoContactoServicio.listarTipoContactoUsuario(propietario.getCliente()));
            } catch (MiException ex) {
            }
        }
        return "perfil_usuario.html";
    }
    
    @PostMapping("/perfil/{id}")
    public String modificar(@RequestParam String nombre,@PathVariable String id, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String password, @RequestParam String password2,
            @RequestParam MultipartFile[] imagenesInput, @RequestParam String descripcion,
            @RequestParam String[] tipoContactoInput, @RequestParam String[] contactosInput,
            @RequestParam(required = false) String[] imagenesViejas) {
        try {
            clienteServicio.modificarCliente(email,id, nombre, apellido, descripcion,
                    password, password2, imagenesInput, tipoContactoInput, contactosInput,imagenesViejas);
        } catch (Exception ex) {
            Logger.getLogger(ClienteControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index.html";
    }
    @PostMapping("/cambiar_password")
    public String cambiandoPassword(@RequestParam String email, @RequestParam String password, @RequestParam String newPassword,@RequestParam String equalPassword, ModelMap model) throws MiException {
        try {
            usuarioServicio.cambiarPassword(email, password,newPassword,equalPassword);

            return "redirect:/index";

        } catch (MiException e) {

            model.addAttribute("error", e.getMessage());

            return "redirect:/perfil_usuario";
        }

    }
}
