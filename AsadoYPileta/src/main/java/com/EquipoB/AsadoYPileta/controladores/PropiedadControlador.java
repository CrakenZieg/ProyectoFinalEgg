
package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.entidades.Imagen;
import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.Servicio;
import com.EquipoB.AsadoYPileta.enumeraciones.TipoPropiedad;
import com.EquipoB.AsadoYPileta.servicios.ImagenServicio;
import com.EquipoB.AsadoYPileta.servicios.PropiedadServicio;
import com.EquipoB.AsadoYPileta.servicios.ServicioServicio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/propiedad")
public class PropiedadControlador {
    
    @Autowired 
    private PropiedadServicio propiedadServicio;
    
    @Autowired 
    private ServicioServicio servicioServicio;
    
    @Autowired 
    private ImagenServicio imagenServicio;
    
    private TipoPropiedad tipos;
    
    @GetMapping("/{tipo}")
    public String listar(@PathVariable String tipo, ModelMap model){
        List<Propiedad> propiedades = new ArrayList<>();
        propiedades = propiedadServicio.listarPropiedadesPorTipo(tipo);
        model.addAttribute("propiedades", propiedades);
        model.addAttribute("tipos", tipos);
        return "index.html";
    }
    
    @GetMapping("/registrar")
    public String registrar(ModelMap model){
        List<Servicio> servicios = new ArrayList<>();
        servicios = servicioServicio.listarServicios();
        model.addAttribute("tipos", tipos);
        model.addAttribute("servicios", servicios);
        return "registro_propiedad.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String titulo, @RequestParam String descripcion, 
            @RequestParam String ubicacion, @RequestParam String direccion, 
            @RequestParam TipoPropiedad tipo, @RequestParam(required = false) String[] serviciosInput, 
            @RequestParam MultipartFile[] imagenesInput, @RequestParam Double valor){            
            List<Servicio> servicios = new ArrayList<>();
            List<Imagen> imagenes = new ArrayList<>();
        try{
            if(serviciosInput != null){
                servicios = servicioServicio.listarServiciosArray(serviciosInput);
            }
            imagenes = imagenServicio.guardarVarias(imagenesInput);
            propiedadServicio.crearPropiedad(titulo, descripcion, ubicacion, 
                direccion, tipo, servicios, imagenes, valor);        
        }catch(Exception ex){
            System.out.println("Excepcion: "+ex);
        }
        return "index.html";
    }
    
    
    
}
