
package com.EquipoB.AsadoYPileta.controladores;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/cliente")
public class ClienteControlador {
    

    
    
    @GetMapping("/registrar")
    public String registrar(){
       
        
        return "registro_usuario.html";
    }
    
 
    
    

}
