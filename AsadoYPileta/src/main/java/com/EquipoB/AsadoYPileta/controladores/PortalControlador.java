package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.enumeraciones.TipoPropiedad;
import com.EquipoB.AsadoYPileta.servicios.PropiedadServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @Autowired
    private PropiedadServicio propiedadServicio;
    
    private TipoPropiedad tipos;
    
    @GetMapping("/")
    public String index(ModelMap model){        
        List<Propiedad> propiedades = propiedadServicio.listarPropiedades();
        model.addAttribute("propiedades", propiedades);        
        model.addAttribute("tipos", tipos);
        return "index.html";
    }
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo ) {
        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos!");
        }
        return "login.html";
    }


}
