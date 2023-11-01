package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Imagen;
import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.Servicio;
import com.EquipoB.AsadoYPileta.enumeraciones.TipoPropiedad;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.PropiedadRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PropiedadServicio {

    @Autowired
    private PropiedadRepositorio propiedadRepositorio;

    @Autowired
    private ServicioServicio servicioServicio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void crearPropiedad(String titulo, String descripcion, String ubicacion, String direccion, TipoPropiedad tipo,
            String[] serviciosInput, MultipartFile[] imagenesInput, Double valor) throws MiException, Exception {

        validar(titulo, descripcion, ubicacion, direccion, tipo, imagenesInput, valor);

        List<Servicio> servicios = new ArrayList<>();
        if (serviciosInput != null) {
            servicios = servicioServicio.listarServiciosArray(serviciosInput);
        }
        List<Imagen> imagenes = new ArrayList<>();
        imagenes = imagenServicio.guardarVarias(imagenesInput);

        Propiedad propiedad = new Propiedad();

        propiedad.setTitulo(titulo);
        propiedad.setDescripcion(descripcion);
        propiedad.setUbicacion(ubicacion);
        propiedad.setDireccion(direccion);
        propiedad.setTipo(tipo);
        propiedad.setEstado(true);
        propiedad.setServicios(servicios);
        propiedad.setImagenes(imagenes);
        propiedad.setValor(valor);
        propiedadRepositorio.save(propiedad);
    }

    @Transactional
    public void modificarPropiedad(String id, String titulo, String descripcion, String ubicacion,
            String direccion, TipoPropiedad tipo, String[] serviciosInput, MultipartFile[] imagenesInput,
            Double valor, String[] imagenesViejas, String estado) throws MiException, Exception {

        validar(titulo, descripcion, ubicacion, direccion, tipo, imagenesInput, valor);

        Optional<Propiedad> propiedadRepo = propiedadRepositorio.findById(id);

        List<Servicio> servicios = new ArrayList<>();
        if (serviciosInput != null) {
            servicios = servicioServicio.listarServiciosArray(serviciosInput);
        }

        if (propiedadRepo.isPresent()) {

            Propiedad propiedad = propiedadRepo.get();
            List<Imagen> imagenes = propiedad.getImagenes();
            
            if(imagenesViejas != null){ 
                if(imagenesViejas.length != 0){
                    imagenes = imagenServicio.filtrar(imagenes, 
                            imagenesViejas);
                }
            }     
            if(imagenesInput != null){
                if(imagenesInput.length != 0){
                    imagenes.addAll(imagenServicio.guardarVarias(imagenesInput));
                } 
            }

            propiedad.setTitulo(titulo);
            propiedad.setDescripcion(descripcion);
            propiedad.setUbicacion(ubicacion);
            propiedad.setDireccion(direccion);            
            if("true".equals(estado)){
                propiedad.setEstado(true);
           }else{
              propiedad.setEstado(false); 
           }
            propiedad.setEstado(Boolean.valueOf(estado));
            propiedad.setTipo(tipo);
            propiedad.setServicios(servicios);
            propiedad.setImagenes(imagenes);
            propiedad.setValor(valor);
            propiedadRepositorio.save(propiedad);
        }
    }

    public List<Propiedad> listarPropiedades() {
        List<Propiedad> propiedades = new ArrayList<>();
        propiedades = propiedadRepositorio.findAll();
        return propiedades;
    }

    public List<Propiedad> listarPropiedadesPorTipo(String tipo) {
        List<Propiedad> propiedades = new ArrayList<>();
        propiedades = propiedadRepositorio.buscarPorTipo(tipo);
        return propiedades;
    }

    public Propiedad getOne(String id) {
        return propiedadRepositorio.getOne(id);
    }

    public void eliminar(String id) {
        Optional<Propiedad> propiedadRepo = propiedadRepositorio.findById(id);
        if (propiedadRepo.isPresent()){
            Propiedad propiedad = propiedadRepo.get();
            propiedadRepositorio.delete(propiedad);
        }        
    }

    public void validar(String titulo, String descripcion, String ubicacion, String direccion,
            TipoPropiedad tipo, MultipartFile[] imagenes, Double valor) throws MiException {
        
        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }

        if (descripcion.isEmpty() || descripcion == null) {
            throw new MiException("La descripcion no puede ser nulo o estar vacio");
        }

        if (ubicacion.isEmpty() || ubicacion == null) {
            throw new MiException("La ubicacion no puede ser nulo o estar vacio");
        }

        if (direccion.isEmpty() || direccion == null) {
            throw new MiException("La direccion no puede ser nulo o estar vacio");
        }

        if (tipo == null) {
            throw new MiException("El tipo no puede ser nulo");
        }

        if (imagenes.length == 0 || imagenes == null) {
            throw new MiException("Las imagenes no puede ser nulas o estar vacias");
        }

        if (valor == null) {
            throw new MiException("El valor no puede ser 0");
        }
    }

}
