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
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public void crearComentario(MultipartFile[] archivos, String cuerpo, Propiedad propiedad, Usuario usuario) throws MiException, Exception {
        validar(cuerpo, archivos, propiedad, usuario);

        Comentario comentario = new Comentario();
        comentario.setCuerpo(cuerpo);
        List<Imagen> imagenes = new ArrayList<>();
        imagenes = imagenServicio.guardarVarias(archivos);
        comentario.setImagenes(imagenes);
        Propiedad Propiedad = propiedadServicio.getOne(usuario.getId());
        comentario.setPropiedad(propiedad);
        comentario.setUsuario(usuario);

        comentarioRepositorio.save(comentario);

    }

    public List<Comentario> listarComentario() {

        List<Comentario> comentarios = new ArrayList();

        comentarios = comentarioRepositorio.findAll();

        return comentarios;
    }

    public void modificarComentario(MultipartFile[] archivos, String id, String cuerpo, Propiedad propiedad, Usuario usuario, String[] imagenesViejas) throws MiException, Exception {
         validar(cuerpo, archivos, propiedad, usuario);
        Optional<Comentario> respuesta = comentarioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Comentario comentario = respuesta.get();
            comentario.setCuerpo(cuerpo);
             List<Imagen> imagenes = propiedad.getImagenes();
            
            if(imagenesViejas != null){ 
                if(imagenesViejas.length != 0){
                    imagenes = imagenServicio.filtrar(imagenes, 
                            imagenesViejas);
                }
            }     
            if(archivos != null){
                if(archivos.length != 0){
                    imagenes.addAll(imagenServicio.guardarVarias(archivos));
                } 
            }

            comentario.setImagenes(imagenes);
            comentario.setPropiedad(propiedad);
            comentario.setUsuario(usuario);
            
            comentarioRepositorio.save(comentario);
        }
    }

    public Comentario getOne(String id) {

        return comentarioRepositorio.getById(id);
    }

    private void validar(String cuerpo, MultipartFile[] imagenes, Propiedad propiedad, Usuario usuario) throws MiException {

        if (cuerpo.isEmpty() || cuerpo == null) {
            throw new MiException("el comentario no puede ser nulo o estar vacío");
        }
        if (imagenes.length == 0 || imagenes == null) {
            throw new MiException("Las imagenes no puede ser nulas o estar vacias");
        }
        if (propiedad == null) {
            throw new MiException("la propiedad no puede no estar cargada");
        }
        if (usuario == null) {
            throw new MiException("el usuario no puede estar vacío");
        }
    }

}
