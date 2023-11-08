
package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.Servicio;
import com.EquipoB.AsadoYPileta.entidades.TipoContacto;
import com.EquipoB.AsadoYPileta.entidades.TipoPropiedad;
import com.EquipoB.AsadoYPileta.entidades.Usuario;
import com.EquipoB.AsadoYPileta.servicios.ClienteServicio;
import com.EquipoB.AsadoYPileta.servicios.PropiedadServicio;
import com.EquipoB.AsadoYPileta.servicios.PropietarioServicio;
import com.EquipoB.AsadoYPileta.servicios.ServicioServicio;
import com.EquipoB.AsadoYPileta.servicios.TipoContactoServicio;
import com.EquipoB.AsadoYPileta.servicios.TipoPropiedadServicio;
import com.EquipoB.AsadoYPileta.servicios.UsuarioServicio;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminControlador {
    
    @Autowired
    private TipoContactoServicio tipoContactoServicio;
    
    @Autowired
    private TipoPropiedadServicio tipoPropiedadServicio;
    
    @Autowired
    private ServicioServicio servicioServicio;
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private ClienteServicio clienteServicio;
    
    @Autowired
    private PropietarioServicio propietarioServicio;
    
    @Autowired
    private PropiedadServicio propiedadServicio;
    
    @GetMapping("dashboard")
    public String dashboard(ModelMap model){
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        List<Propiedad> propiedades = propiedadServicio.listarPropiedades();
        List<Servicio> servicios = servicioServicio.listarServicios();
        List<TipoContacto> tipoContactos = tipoContactoServicio.listarTipoContacto();
        List<TipoPropiedad> tipoPropiedades = tipoPropiedadServicio.listarTipoPropiedad();
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("propiedades", propiedades);
        model.addAttribute("servicios", servicios);
        model.addAttribute("tipoContactos", tipoContactos);
        model.addAttribute("tipoPropiedades", tipoPropiedades);
        return "panel_admin2.html";
    }
    
    
}
