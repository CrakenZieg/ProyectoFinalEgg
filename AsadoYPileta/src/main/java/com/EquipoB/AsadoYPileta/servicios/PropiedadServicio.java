package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Imagen;
import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.Propietario;
import com.EquipoB.AsadoYPileta.entidades.Servicio;
import com.EquipoB.AsadoYPileta.entidades.Usuario;
import com.EquipoB.AsadoYPileta.enumeraciones.TipoPropiedad;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.PropiedadRepositorio;
import com.EquipoB.AsadoYPileta.repositorios.PropietarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PropiedadServicio {

    @Autowired
    private PropiedadRepositorio propiedadRepositorio;

    @Autowired
    private PropietarioServicio propietarioServicio;
    
    @Autowired
    private PropietarioRepositorio propietarioRepositorio;
    
    @Autowired
    private ServicioServicio servicioServicio;

    @Autowired
    private ImagenServicio imagenServicio;
    
    @Autowired
    private ReservaServicio reservaServicio;        

    @Transactional
    public void crearPropiedad(String titulo, String descripcion, String ubicacion, String direccion, TipoPropiedad tipo,
            String[] serviciosInput, MultipartFile[] imagenesInput, Double valor, Usuario usuario) throws MiException, Exception {

        validar(titulo, descripcion, ubicacion, direccion, tipo, imagenesInput, valor);
        
        Optional<Propietario> respuesta = propietarioRepositorio.findById(usuario.getId());


        Propietario propietario = null;       
      

        if(respuesta.isPresent()){

            propietario = respuesta.get();
        } else {
            propietario = propietarioServicio.crearPropietario(usuario);
        }        

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
        if(propietario.getPropiedades() != null){
            propietario.getPropiedades().add(propiedad);
        } else {
            List<Propiedad> propiedades = new ArrayList<>();
            propiedades.add(propiedad);
            propietario.setPropiedades(propiedades);
        }
        propietarioRepositorio.save(propietario);
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
                boolean busqueda= reservaServicio.validarReservasPropiedad(id);
                if(busqueda == true){
                    throw new MiException("No puede darse de baja si tiene reservas activas!");
                }else{
                    propiedad.setEstado(false);
                }
             
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

    @Transactional
    public void darDeBaja(String id) {
        Propiedad propiedad = propiedadRepositorio.getOne(id);
        propiedad.setEstado(false);
        propiedadRepositorio.save(propiedad);
    }
    
    @Transactional
    public void darDeAlta(String id) {
        Propiedad propiedad = propiedadRepositorio.getOne(id);
        propiedad.setEstado(true);
        propiedadRepositorio.save(propiedad);
    }

    @Transactional
    public void eliminar(String id, Usuario logueado) {
        Optional<Propiedad> propiedadRepo = propiedadRepositorio.findById(id);
        Optional<Propietario> propietarioRepo = propietarioRepositorio.findById(logueado.getId());
        if (propiedadRepo.isPresent() && propietarioRepo.isPresent()){
            Propiedad propiedad = propiedadRepo.get();
            Propietario propietario = propietarioRepo.get();
            List<Propiedad> propiedades = propietario.getPropiedades();
            if(propiedades.contains(propiedad)){
                propiedades.remove(propiedad);
                propietario.setPropiedades(propiedades);
                propietarioRepositorio.save(propietario);
                propiedadRepositorio.delete(propiedad);
            }
        }        
    }

    public void validar(String titulo, String descripcion, String ubicacion, String direccion,
            TipoPropiedad tipo, MultipartFile[] imagenes, Double valor) throws MiException {
        
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }

        if ( descripcion == null || descripcion.trim().isEmpty() ) {
            throw new MiException("La descripcion no puede ser nulo o estar vacio");
        }

        if (ubicacion == null || ubicacion.trim().isEmpty() ) {
            throw new MiException("La ubicacion no puede ser nulo o estar vacio");
        }

        if ( direccion == null || direccion.trim().isEmpty() ) {
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
