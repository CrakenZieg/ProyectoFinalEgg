package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.entidades.Cliente;
import com.EquipoB.AsadoYPileta.entidades.Comentario;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.servicios.ComentarioServicio;
import com.EquipoB.AsadoYPileta.servicios.ImagenServicio;
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
import com.EquipoB.AsadoYPileta.servicios.ClienteServicio;
import com.EquipoB.AsadoYPileta.servicios.PropiedadServicio;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/comentario")
public class ComentarioControlador {

    @Autowired
    private ComentarioServicio comentarioServicio;
    @Autowired
    private ImagenServicio imagenServicio;
    @Autowired
    private PropiedadServicio propiedadServicio;
    @Autowired
    private ClienteServicio clienteServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        if (usuario.getRol().equals(Rol.CLIENTE)) {
            Cliente cliente = clienteServicio.getOne(usuario.getId());
            modelo.put("cliente", cliente);

        }
        return "comentario_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam() String cuerpo, HttpSession session, @RequestParam MultipartFile[] archivos, 
                            @RequestParam String stringIdpropiedad,@RequestParam Integer puntuacion, ModelMap modelo) throws Exception {
        try {

            comentarioServicio.crearComentario(session, archivos, cuerpo, stringIdpropiedad, puntuacion);

            modelo.put("exito", "El comentario fue registrado correctamente!");
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "comentario_form.html";
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Comentario> comentarios = comentarioServicio.listarComentario();
        modelo.addAttribute("comentarios", comentarios);

        return "comentario_list.html";
    }

    @GetMapping("/listaIdUsuario")
    public String listarIdUsuario(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        if (usuario.getRol().equals(Rol.CLIENTE)) {
            Cliente cliente = clienteServicio.getOne(usuario.getId());
            modelo.put("cliente", cliente);

        }
        List<Comentario> comentarios = comentarioServicio.findComentariosByUserId(usuario.getId());
        modelo.addAttribute("comentarios", comentarios);

        return "comentario_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificarComentario(@PathVariable String id, HttpSession session, ModelMap modelo) {
        modelo.put("comentario", comentarioServicio.getOne(id));
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        if (usuario.getRol().equals(Rol.CLIENTE)) {
            Cliente cliente = clienteServicio.getOne(usuario.getId());
            modelo.put("cliente", cliente);

        }
        return "comentario_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String actualizar(@RequestParam MultipartFile[] archivos, @PathVariable String id, HttpSession session, @RequestParam String cuerpo, Cliente usuario, @RequestParam String stringIdpropiedad, @RequestParam(required = false) String[] imagenesViejas, Integer puntuacion, ModelMap modelo) throws Exception {

        try {

            comentarioServicio.modificarComentario(session, archivos, id, cuerpo, stringIdpropiedad, imagenesViejas, puntuacion);

            modelo.put("exito", "comentario actualizado correctamente!");

            return "index.html";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "comentario_modificar.html";
        }
    }

    @GetMapping("/borrar/{id}")
    public String borrarComentario(@PathVariable String id) throws MiException {

        comentarioServicio.eliminarComentrario(id);

        return "redirect:/comentario/lista";

    }
}
