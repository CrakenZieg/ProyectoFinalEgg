
package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.entidades.Contacto;
import com.EquipoB.AsadoYPileta.entidades.Imagen;
import com.EquipoB.AsadoYPileta.enumeraciones.Rol;
import com.EquipoB.AsadoYPileta.servicios.ClienteServicio;
import com.EquipoB.AsadoYPileta.servicios.UsuarioServicio;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cliente")
public class ClienteControlador {
    
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ClienteServicio clienteServicio;
    
    
    @GetMapping("/registrar")
    public String registrar(){
       
        
        return "registro_usuario.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String email,
          @RequestParam String password, @RequestParam Rol rol, @RequestParam Date fechaAlta,
          @RequestParam Boolean alta, @RequestParam List<Imagen> imagenes, @RequestParam String descrpcion, 
          @RequestParam List<Contacto> contactos){
        
        try {
            usuarioServicio.crearUsuario(nombre, apellido, email, password, rol, fechaAlta, alta);
        } catch (Exception ex) {
            Logger.getLogger(ClienteControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return "index.html";
    }
    
    
}
