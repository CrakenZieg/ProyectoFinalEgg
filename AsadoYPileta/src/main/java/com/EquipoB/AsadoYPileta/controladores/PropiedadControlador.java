
package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.entidades.Imagen;
import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.Servicio;
import com.EquipoB.AsadoYPileta.enumeraciones.TipoPropiedad;
import com.EquipoB.AsadoYPileta.servicios.PropiedadServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/propiedad")
public class PropiedadControlador {
    
    @Autowired 
    private PropiedadServicio propiedadServicio;
    
    @GetMapping("/{tipo}")
    public String listar(@PathVariable String tipo, ModelMap model){
        List<Propiedad> propiedades = propiedadServicio.listarPropiedadesPorTipo(tipo);
        model.addAttribute("propiedades", propiedades);
        return "index.html";
    }
    
    @GetMapping("/registrar")
    public String registrar(){
        return "registro_propiedad.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String titulo, @RequestParam String descripcion, 
            @RequestParam String ubicacion, @RequestParam String direccion, 
            @RequestParam TipoPropiedad tipo, @RequestParam List<Servicio> servicios, 
            @RequestParam List<Imagen> imagenes, @RequestParam Double valor){
        try{
            propiedadServicio.crearPropiedad(titulo, descripcion, ubicacion, 
                direccion, tipo, servicios, imagenes, valor);        
        }catch(Exception ex){
            System.out.println("Excepcion: "+ex);
        }
        return "index.html";
    }
    
    
    
}
