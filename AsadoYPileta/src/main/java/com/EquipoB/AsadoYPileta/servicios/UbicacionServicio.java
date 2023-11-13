
package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Ubicacion;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.UbicacionRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UbicacionServicio {
    @Autowired 
    private UbicacionRepositorio ubicacionRepositorio;
    
    @Transactional
   public Ubicacion crearUbicacion (String pais, String provincia,String departamento, String localidad,String calle,String numeracion,String observaciones,
                               Double latitud, Double longitud) throws MiException{
       
        validar(pais, provincia, departamento, localidad, calle, numeracion, observaciones, latitud, longitud);
       Ubicacion ubicacion = new Ubicacion();
       
       ubicacion.setPais(pais);
       ubicacion.setProvincia(provincia);
       ubicacion.setDepartamento(departamento);
       ubicacion.setLocalidad(localidad);
       ubicacion.setCalle(calle);
       ubicacion.setNumeracion(numeracion);
       ubicacion.setObservaciones(observaciones);
       ubicacion.setLatitud(latitud);
       ubicacion.setLongitud(longitud);
       ubicacion.setEstado(true);
       ubicacionRepositorio.save(ubicacion);
       return ubicacion;
       
   }
   @Transactional
    public Ubicacion modificarUbicacion (String id,String pais, String provincia,String departamento, String localidad,String calle,String numeracion,String observaciones,
                               Double latitud, Double longitud,String estadoPropiedad) throws MiException{
       validar(pais, provincia, departamento, localidad, calle, numeracion, observaciones, latitud, longitud);
       Optional<Ubicacion> respuesta = ubicacionRepositorio.findById(id);
       
       if(respuesta.isPresent()){
           Ubicacion ubicacion = respuesta.get();
           ubicacion.setPais(pais);
           ubicacion.setProvincia(provincia);
           ubicacion.setDepartamento(departamento);
           ubicacion.setLocalidad(localidad);
           ubicacion.setCalle(calle);
           ubicacion.setNumeracion(numeracion);
           ubicacion.setObservaciones(observaciones);
           ubicacion.setLatitud(latitud);
           ubicacion.setLongitud(longitud);
           if ("true".equals(estadoPropiedad)) {
                ubicacion.setEstado(true);
            } else {
                ubicacion.setEstado(false);
            }
           ubicacionRepositorio.save(ubicacion);
           return ubicacion;
       }else{
           throw new MiException("ubicacion no encontrada");
       }
     
   }
    @Transactional
    // al dar de baja/alta la propiedad cambiar el estado de la ubicacion
    public void cambiarEstadoUbicacion(String id, String estadoPropiedad) throws MiException {
        Optional<Ubicacion> respuesta = ubicacionRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Ubicacion ubicacion = respuesta.get();
            if ("true".equals(estadoPropiedad)) {
                ubicacion.setEstado(true);
                ubicacionRepositorio.save(ubicacion);
            } else {
                ubicacion.setEstado(false);
                ubicacionRepositorio.save(ubicacion);
            }
        }else{
           throw new MiException("ubicacion no encontrada");
       }
    }
   @Transactional(readOnly = true)
    public Ubicacion getOne (String id) throws MiException{
         Optional<Ubicacion> respuesta = ubicacionRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new MiException("No se encontro la ubicacion");
        }
    }
    
    public void validar (String pais, String provincia,String departamento, String localidad,String calle,String numeracion,String observaciones,
                               Double latitud, Double longitud) throws MiException{
         if (pais == null || pais.trim().isEmpty()) {

                throw new MiException("el pais no puede ser nulo o estar vacío");
            }
         
         if (provincia == null || provincia.trim().isEmpty()) {

                throw new MiException("la provincia no puede ser nulo o estar vacío");
            }
         if (departamento == null || departamento.trim().isEmpty()) {

                throw new MiException("el departemento no puede ser nulo o estar vacío");
            }
         if (localidad== null || localidad.trim().isEmpty()) {

                throw new MiException("la localidad no puede ser nulo o estar vacío");
            }
         if (numeracion == null || numeracion.trim().isEmpty()) {

                throw new MiException("la numeracion no puede ser nulo o estar vacío");
            }
         if (observaciones == null || observaciones.trim().isEmpty()) {

                throw new MiException("la observacion no puede ser nula o estar vacío");
            }
         if (calle == null || calle.trim().isEmpty()) {

                throw new MiException("la calle no puede ser nula o estar vacío");
            }
         if (latitud == null) {

            throw new MiException("la latitud no puede ser nula o estar vacía");
        }
        if (longitud == null) {

            throw new MiException("la longitud no puede ser nula o estar vacía");
        }

    }
  
}
