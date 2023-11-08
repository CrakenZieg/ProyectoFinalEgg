package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Comentario;
import com.EquipoB.AsadoYPileta.entidades.Imagen;
import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.Usuario;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.ComentarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ComentarioServicio {

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;
    @Autowired
    private PropiedadServicio propiedadServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @Transactional
    public void crearComentario(HttpSession session, MultipartFile[] archivos, String cuerpo, String stringIdpropiedad) throws MiException, Exception {
        validar(session, cuerpo, archivos, stringIdpropiedad);

        Comentario comentario = new Comentario();
        comentario.setCuerpo(cuerpo);
        List<Imagen> imagenes = new ArrayList<>();
        imagenes = imagenServicio.guardarVarias(archivos);
        comentario.setImagenes(imagenes);
        Propiedad propiedad = propiedadServicio.getOne(stringIdpropiedad);
        comentario.setPropiedad(propiedad);
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        comentario.setUsuario(logueado);

        comentarioRepositorio.save(comentario);

    }

    public List<Comentario> listarComentario() {

        List<Comentario> comentarios = new ArrayList();

        comentarios = comentarioRepositorio.findAll();

        return comentarios;
    }

    public List<Comentario> findComentariosByUserId(String userId) {
        return comentarioRepositorio.buscarPorCliente(userId);
    }

    public void modificarComentario(HttpSession session, MultipartFile[] archivos, String id, String cuerpo, String stringIdpropiedad, String[] imagenesViejas) throws MiException, Exception {
        validar(session, cuerpo, archivos, stringIdpropiedad);
        Optional<Comentario> respuesta = comentarioRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Comentario comentario = respuesta.get();
            comentario.setCuerpo(cuerpo);
            List<Imagen> imagenes = comentario.getImagenes();

            if (imagenesViejas != null) {
                if (imagenesViejas.length != 0) {
                    imagenes = imagenServicio.filtrar(imagenes,
                            imagenesViejas);
                }
            }
            if (archivos != null) {
                if (archivos.length != 0) {
                    imagenes.addAll(imagenServicio.guardarVarias(archivos));
                }
            }
            Propiedad propiedad = propiedadServicio.getOne(stringIdpropiedad);
            comentario.setPropiedad(propiedad);
            comentario.setImagenes(imagenes);

            Usuario logueado = (Usuario) session.getAttribute("usuariosession");
            comentario.setUsuario(logueado);

            comentarioRepositorio.save(comentario);
        }
    }

    public Comentario getOne(String id) {

        return comentarioRepositorio.getById(id);
    }

    @Transactional
    public void eliminarComentrario(String id) throws MiException {
        Comentario comentario = comentarioRepositorio.getById(id);
        comentarioRepositorio.delete(comentario);
    }

    private void validar(HttpSession session, String cuerpo, MultipartFile[] imagenes, String stringIdpropiedad) throws MiException {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado == null) {
            throw new MiException("el usuario debe estar logueado");
        }
        if (cuerpo.isEmpty() || cuerpo == null) {

            if (cuerpo == null || cuerpo.trim().isEmpty()) {

                throw new MiException("el comentario no puede ser nulo o estar vacío");
            }
            if (imagenes.length == 0 || imagenes == null) {
                throw new MiException("Las imagenes no puede ser nulas o estar vacias");
            }

            if (stringIdpropiedad == null || stringIdpropiedad.trim().isEmpty()) {
                throw new MiException("la propiedad no puede  estar vacia");

            }

        }

    }
}
