
package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.entidades.Servicio;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.servicios.ServicioServicio;
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
@RequestMapping("/servicio")
public class ServicioControlador {
    
    @Autowired
    private ServicioServicio servicioServicio;
    
    @GetMapping("/registrar")  //localhost:8080/servicio/registrar
    public String registrar() {

        return "servicio_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String tipoComodidad, @RequestParam Double valor, ModelMap modelo) {

        try {

            servicioServicio.crearSercicio(tipoComodidad, valor);

            modelo.put("exito", "La comodidad fue cargado correctamente");

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "servicio_form.html";
        }

        return "index.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Servicio> servicios = servicioServicio.listarServicios();

        modelo.addAttribute("servicios", servicios);

        return "servicio_list.html";

    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {

        modelo.put("servicio", servicioServicio.getOne(id));

        return "servicio_modificar.html";

    }
    
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String tipoComodidad, Double valor,  ModelMap modelo) throws MiException{
        
        try {
            servicioServicio.modificarServicio(tipoComodidad, id, valor);
            
            return "redirect:../lista";
            
        } catch (MiException ex) {
            
            modelo.put("error", ex.getMessage());
            
            return "servicio_modificar.html";
            
        }
        
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, String tipoComodidad, Double valor,  ModelMap modelo) throws MiException{
    
        try {
            servicioServicio.eliminarServicio(id, tipoComodidad, valor);
            
            return "redirect:../lista";
            
        } catch (MiException ex) {
            
            modelo.put("error", "No se elimino el servicio");
            
            return "servicio_eliminar.html";
        }
    
    }
    
    
}
