
package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.Reserva;
import com.EquipoB.AsadoYPileta.entidades.Usuario;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.servicios.PropiedadServicio;
import com.EquipoB.AsadoYPileta.servicios.ReservaServicio;
import com.EquipoB.AsadoYPileta.servicios.UsuarioServicio;
import java.util.ArrayList;
import java.util.Date;
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
@RequestMapping("/reserva") //localhost:8080/reserva
public class ReservaControlador {
    
    @Autowired
    private ReservaServicio reservaServicio;
    @Autowired
    private PropiedadServicio propiedadServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("/registrar")  //localhost:8080/reserva/registrar
    public String crearReserva(ModelMap modelo){

     modelo.addAttribute("reservas", new Reserva());
        
        return "reserva.html";
   }
   
   
    @GetMapping("/listar")  //localhost:8080/reserva/listar
   public String listarReservas(ModelMap modelo){
       List <Reserva> reservas = reservaServicio.listarReserva();
       modelo.addAttribute("reservas", reservas);
       
       return "reserva_lista.html";
   }
    
   @PostMapping("/registro")
   public String registroReserva(String id, String mensaje, Date fechaInicio, Date fechaFin, List serviciosElegidas, Double montoTotal, Boolean disponible,ModelMap modelo){
   
       try{
        reservaServicio.crearReserva( mensaje, fechaInicio, fechaFin, serviciosElegidas, montoTotal, disponible);
        return "redirect:/reserva/listar";
      }   catch(MiException e){
          
          modelo.addAttribute("error", e.getMessage());
          
          return "reserva.html";
      }
   }
   
   @GetMapping("/modificar/{id}")
   public String modificarReserva(@PathVariable String id,ModelMap modelo){
       
       modelo.put("reserva", reservaServicio.getOne(id));
       
       return "reserva_modificar.html";
       
   }
   
   @PostMapping("/modificar/{id}")
   public String modificarReserva(@PathVariable String id,String mensaje, Date fechaInicio, Date fechaFin, List serviciosElegidas, Double montoTotal, Boolean disponible,ModelMap modelo){
       
       try{
           reservaServicio.modificarReserva(id, mensaje, fechaInicio, fechaFin, serviciosElegidas, montoTotal, disponible);
           
           return "redirect:/reserva/listar";
       } catch (MiException e){
           
           modelo.addAttribute("error", e.getMessage());
           
           return "reserva_modificar";
       }
          
   }
   
   @GetMapping("/borrar/{id}")
   public String borrarReserva(@PathVariable String id){
       
       reservaServicio.borrar(id);
       
       return "redirect:/reserva/listar";
       
   }
   
 
}
