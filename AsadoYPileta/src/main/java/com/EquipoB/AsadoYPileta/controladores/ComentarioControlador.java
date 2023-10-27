
package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.entidades.Imagen;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.servicios.ComentarioServicio;

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
@RequestMapping("/comentario")
public class ComentarioControlador {
    @Autowired
    private ComentarioServicio comentarioServicio;
    
    @GetMapping("/registrar")
    public String registrar(){
        return "comentario_form.html";
    }
     @PostMapping("/registro")
    public String registro(@RequestParam() String cuerpo,List<Imagen> imagenes, MultipartFile archivo, ModelMap modelo){
        try {
            comentarioServicio.crearComentario(archivo, cuerpo, imagenes);
            modelo.put("exito", "El comentario fue registrado correctamente!");
        } catch (MiException ex) {
                      
            modelo.put("error", ex.getMessage());
            
            return "comentario_form.html";
        }
       
        
        return "index.html";        
    }
    
}
