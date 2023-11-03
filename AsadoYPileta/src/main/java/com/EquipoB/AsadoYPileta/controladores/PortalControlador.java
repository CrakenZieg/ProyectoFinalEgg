package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.enumeraciones.Rol;
import com.EquipoB.AsadoYPileta.enumeraciones.TipoPropiedad;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.servicios.PropiedadServicio;
import com.EquipoB.AsadoYPileta.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @Autowired
    private PropiedadServicio propiedadServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    private TipoPropiedad tipos;
    
    @GetMapping("/")
    public String index(ModelMap model){        
        List<Propiedad> propiedades = propiedadServicio.listarPropiedades();
        model.addAttribute("propiedades", propiedades);        
        model.addAttribute("tipos", tipos);
        return "index.html";
    }
    

    @GetMapping("/registrar")
    public String registrar (ModelMap modelo){
        return "registro.html";
    }
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo ) {
        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos!");
        }
        return "login.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String email, @RequestParam String password, 
            @RequestParam Rol rol, ModelMap modelo) throws Exception {
        try {
            usuarioServicio.crearUsuario(email, email, email, password, password, rol);
            return "index.html";
        } catch (MiException ex) {           
            return "registro.html";
        }
    }

}
