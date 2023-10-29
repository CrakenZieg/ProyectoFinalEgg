
package com.EquipoB.AsadoYPileta.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/propiedad")
public class PropiedadControlador {
    
    @GetMapping("/{tipo}")
    public String listar(){
        return "propiedad_tipo.html";
    }
    
    @GetMapping("/registrar")
    public String registrar(){
        return "registro_propiedad.html";
    }
    
}
