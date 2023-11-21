package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.entidades.Cliente;
import com.EquipoB.AsadoYPileta.entidades.Comentario;
import com.EquipoB.AsadoYPileta.entidades.Reserva;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.servicios.ComentarioServicio;
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
import com.EquipoB.AsadoYPileta.entidades.Usuario;
import com.EquipoB.AsadoYPileta.enumeraciones.Rol;
import com.EquipoB.AsadoYPileta.excepciones.PermisosException;
import com.EquipoB.AsadoYPileta.repositorios.ReservaRepositorio;
import com.EquipoB.AsadoYPileta.servicios.ClienteServicio;
import javax.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/comentario")
public class ComentarioControlador {

    @Autowired
    private ComentarioServicio comentarioServicio;
    @Autowired
    private ReservaRepositorio reservaServicio;
    @Autowired
    private ClienteServicio clienteServicio;

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_PROPIETARIO')")
    @GetMapping("/registrar/{id}")
    public ModelAndView registrar(@PathVariable String id, ModelMap modelo, HttpSession session) throws PermisosException {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        Cliente cliente = clienteServicio.getOne(usuario.getId());
        Reserva reserva = reservaServicio.getOne(id);
        if(!cliente.getId().equals(reserva.getCliente().getId())){
            throw new PermisosException("No es posible realizar comentarios en base a una reserva ajena");
        }
        modelo.put("cliente", cliente);
        modelo.put("reserva", reserva);        
        return new ModelAndView("comentario_form.html", modelo);
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_PROPIETARIO')")
    @PostMapping("/registro")
    public ModelAndView registro(@RequestParam() String cuerpo, @RequestParam String idReserva, @RequestParam String idCliente, @RequestParam MultipartFile[] imagenes, 
                            @RequestParam String idPropiedad, @RequestParam double puntuacion, HttpSession session) throws Exception {       
        comentarioServicio.crearComentario(cuerpo, idReserva, idCliente, imagenes, idPropiedad, puntuacion, session);
        return new ModelAndView( "redirect:/cliente/perfil");
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_PROPIETARIO','ROLE_ADMIN')")
    @GetMapping("/borrar/{id}")
    public String borrarComentario(@PathVariable String id) throws MiException {
        comentarioServicio.eliminarComentrario(id);
        return "redirect:/comentario/lista";
    }
}
