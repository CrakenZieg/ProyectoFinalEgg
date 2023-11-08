
package com.EquipoB.AsadoYPileta.controladores;


import com.EquipoB.AsadoYPileta.entidades.Usuario;
import com.EquipoB.AsadoYPileta.enumeraciones.Rol;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.excepciones.PermisosException;
import com.EquipoB.AsadoYPileta.servicios.ClienteServicio;
import com.EquipoB.AsadoYPileta.servicios.TipoContactoServicio;
import com.EquipoB.AsadoYPileta.servicios.UsuarioServicio;
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
    private Rol rol;

    
    @GetMapping("/registrar")
    public String registrar(ModelMap model){
        model.addAttribute("tipoContacto", tipoContactoServicio.listarTipoContacto());
        return "registro_usuario.html";
    }
    

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido, 
        @RequestParam String email, @RequestParam String password, @RequestParam String password2,
        @RequestParam MultipartFile[] imagenesInput, @RequestParam String descripcion, 
        @RequestParam String[] tipoContactoInput, @RequestParam String[] contactosInput){        
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
    
}
    

