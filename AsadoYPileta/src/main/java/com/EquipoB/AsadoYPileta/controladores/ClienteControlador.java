
package com.EquipoB.AsadoYPileta.controladores;



import com.EquipoB.AsadoYPileta.servicios.ClienteServicio;
import com.EquipoB.AsadoYPileta.servicios.TipoContactoServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/cliente")
public class ClienteControlador {
    
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private TipoContactoServicio tipoContactoServicio;

    
    @GetMapping("/registrar")
    public String registrar(ModelMap model){
        model.addAttribute("tipoContacto", tipoContactoServicio.listarTipoContacto());
        return "registro_usuario.html";
    }
    

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido, 
        @RequestParam String email, @RequestParam String password, @RequestParam String password2,
        @RequestParam MultipartFile[] imagenesInput, @RequestParam String descripcion, 
        @RequestParam String[] tipoContactoInput, @RequestParam String[] contactosInput){        
        try {
            clienteServicio.crearCliente(email, nombre, apellido, descripcion, 
                    password, password2, imagenesInput, tipoContactoInput, contactosInput);
        } catch (Exception ex) {
            Logger.getLogger(ClienteControlador.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return "index.html";
    }

    
}
