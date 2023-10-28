<<<<<<< HEAD

=======
>>>>>>> bb980e3e8152683209dadd42d1a65a1b3d61363e
package com.EquipoB.AsadoYPileta.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {
<<<<<<< HEAD


    @GetMapping("/")
    public String index() {
        return "index.html";
    }


=======

    @GetMapping("/")
    public String index(){
        return "index.html";
    }
    
>>>>>>> bb980e3e8152683209dadd42d1a65a1b3d61363e
}
