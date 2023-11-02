
package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.Reserva;
import com.EquipoB.AsadoYPileta.entidades.Usuario;
import com.EquipoB.AsadoYPileta.servicios.PropiedadServicio;
import com.EquipoB.AsadoYPileta.servicios.ReservaServicio;
import com.EquipoB.AsadoYPileta.servicios.UsuarioServicio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/reserva") //localhost:8080/reserva
public class ReservaControlador {
    
    @Autowired
    private ReservaServicio reservaServicio;
    @Autowired
    private PropiedadServicio propiedadServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("/registrar")
    public String crearReserva(ModelMap modelo){

        List <Usuario> usuarios =  usuarioServicio.listarUsuarios();
        List <Propiedad> propiedades = propiedadServicio.listarPropiedades();
        
        modelo.addAttribute("usuarios", usuarios);
        modelo.addAttribute("propiedades",propiedades);
        
        return "ver_reserva.html";
   }
   
    @GetMapping("/{id}")
    public String reserva(@PathVariable String id,ModelMap modelo){
        
        List <Reserva> reservas = new ArrayList <>();
        
        reservas = reservaServicio.listarReserva();
        
        //falta terminar
        
        return "reserva.html";
    }
   
}
