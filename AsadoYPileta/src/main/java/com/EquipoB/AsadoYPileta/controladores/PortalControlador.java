package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.TipoPropiedad;
import com.EquipoB.AsadoYPileta.entidades.Usuario;
import com.EquipoB.AsadoYPileta.servicios.PropiedadServicio;
import com.EquipoB.AsadoYPileta.servicios.TipoPropiedadServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
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
    
    @Autowired
    private TipoPropiedadServicio tipopropiedadServicio;
    
    @GetMapping("/")
    public String index(ModelMap model){         
        List<Propiedad> propiedades = propiedadServicio.listarPropiedadesActivas();
        List<TipoPropiedad> tipoPropiedades = tipopropiedadServicio.listarTipoPropiedad();
        model.addAttribute("propiedades", propiedades);        
        model.addAttribute("tipoPropiedades", tipoPropiedades);
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
