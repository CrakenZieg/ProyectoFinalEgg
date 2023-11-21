package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Cliente;
import com.EquipoB.AsadoYPileta.entidades.Comentario;
import com.EquipoB.AsadoYPileta.entidades.Imagen;
import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.Reserva;
import com.EquipoB.AsadoYPileta.entidades.Usuario;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.ComentarioRepositorio;
import com.EquipoB.AsadoYPileta.repositorios.PropiedadRepositorio;
import com.EquipoB.AsadoYPileta.repositorios.ReservaRepositorio;
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
    private PropiedadRepositorio PropiedadRepositorio;
    @Autowired
    private ReservaRepositorio reservaRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;
    @Autowired
    private PropiedadServicio propiedadServicio;
    @Autowired
    private ClienteServicio clienteServicio;

    @Transactional
    public void crearComentario(String cuerpo, String idReserva, String idCliente, MultipartFile[] archivos, 
                             String idPropiedad,  double puntuacion, HttpSession session) throws MiException, Exception {
        validar(session, idCliente, cuerpo, archivos, idPropiedad,puntuacion);
        Comentario comentario = new Comentario();
        Reserva reserva = reservaRepositorio.getById(idReserva);
        comentario.setReserva(reserva);
        comentario.setCuerpo(cuerpo);
        List<Imagen> imagenes = new ArrayList<>();
        imagenes = imagenServicio.guardarVarias(archivos);
        comentario.setImagenes(imagenes);
        Propiedad propiedad = propiedadServicio.getOne(idPropiedad);
        comentario.setPropiedad(propiedad);
        Cliente cliente = clienteServicio.getOne(idCliente);
        comentario.setCliente(cliente);
        comentario.setPuntuacion(puntuacion);
        comentarioRepositorio.save(comentario);
        calcularPuntuacion(propiedad.getId());
    }

    public List<Comentario> listarComentario() {

        List<Comentario> comentarios = new ArrayList();

        comentarios = comentarioRepositorio.findAll();

        return comentarios;
    }

    public List<Comentario> findComentariosByUserId(String userId) {
        return comentarioRepositorio.buscarPorCliente(userId);
    }

    public List<Comentario> findComentariosByPropiedadId(String propId) {
        return comentarioRepositorio.buscarPorPropiedad(propId);
    }

    private void calcularPuntuacion(String id){
        double suma=0;
        List<Comentario> comentarios = comentarioRepositorio.buscarPorPropiedad(id);
        for (Comentario comentario : comentarios) {
            suma += comentario.getPuntuacion();
        }
        propiedadServicio.setPuntuacion(suma/comentarios.size(), id);      
    }

    public void modificarComentario(HttpSession session, MultipartFile[] archivos, String id, String cuerpo, 
            String idPropiedad, String[] imagenesViejas, Integer puntuacion) throws MiException, Exception {        
        Optional<Comentario> respuesta = comentarioRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Comentario comentario = respuesta.get();
            validar(session, comentario.getCliente().getId(), cuerpo, archivos, idPropiedad,puntuacion);
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
            Propiedad propiedad = propiedadServicio.getOne(idPropiedad);
            comentario.setPropiedad(propiedad);
            comentario.setImagenes(imagenes);
            comentario.setPuntuacion(puntuacion);            

            comentarioRepositorio.save(comentario);
            propiedadServicio.setPuntuacion(obtenerPromedioPuntuacionPorPropiedad(idPropiedad),idPropiedad);
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

    public double obtenerPromedioPuntuacionPorPropiedad(String stringIdpropiedad) {


        return PropiedadRepositorio.obtenerPromedioPuntuacionPorPropiedad(stringIdpropiedad);
    }

    private void validar(HttpSession session, String idCliente, String cuerpo, MultipartFile[] imagenes, String stringIdpropiedad, double puntuacion) throws MiException {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado == null || !logueado.getId().equals(idCliente)) {
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
            if (puntuacion == 0 ) {

                throw new MiException("la puntucion debe cargarse");
            }


        }

    }
}
