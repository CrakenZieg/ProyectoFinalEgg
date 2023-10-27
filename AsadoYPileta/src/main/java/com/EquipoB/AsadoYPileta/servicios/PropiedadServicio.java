
package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Comentario;
import com.EquipoB.AsadoYPileta.entidades.Imagen;
import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.Reserva;
import com.EquipoB.AsadoYPileta.entidades.Servicio;
import com.EquipoB.AsadoYPileta.enumeraciones.TipoPropiedad;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropiedadServicio {
    
    @Autowired
    private PropiedadRepositorio propiedadRepositorio;
    
    @Transactional
    public void crearPropiedad(String titulo, String descripcion, String ubicacion, String direccion, TipoPropiedad tipo,
     List<Servicio> servicios, List<Imagen> imagenes, Double valor, List<Reserva> reservas, List<Comentario> comentarios) throws MiException{
        
        try {
            validar(titulo, descripcion, ubicacion, direccion, tipo, servicios, imagenes, valor, reservas, comentarios);
            
            Propiedad propiedad = new Propiedad();
        
        propiedad.setTitulo(titulo);
        propiedad.setDescripcion(descripcion);
        propiedad.setUbicacion(ubicacion);
        propiedad.setDireccion(direccion);
        propiedad.setTipo(tipo);
        propiedad.setServicios(servicios);
        propiedad.setImagenes(imagenes);
        propiedad.setValor(valor);
        propiedad.setReservas(reservas);
        propiedad.setComentarios(comentarios);
        
        
        propiedadRepositorio.save(propiedad);
            
        } catch (MiException ex) {
            ex.getMessage();
        }
           
    }
    
    public List<Propiedad> listarPropiedades(){
    
    List<Propiedad> propiedades = new ArrayList();
    
    return propiedadRepositorio.finAll();
    
    }
    
    
    
    
    public void validar(String titulo, String descripcion, String ubicacion, String direccion, TipoPropiedad tipo,
       List<Servicio> servicios, List<Imagen> imagenes, Double valor, List<Reserva> reservas, List<Comentario> comentarios) throws MiException{
    
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
    
    if (servicios.isEmpty() || servicios == null) {

            throw new MiException("Los servicios no puede ser nulos o estar vacios");
        }
    
    if (imagenes.isEmpty() || imagenes == null) {

            throw new MiException("Las imagenes no puede ser nulas o estar vacias");
        }
    
    if (valor == null) {

            throw new MiException("El valor de  no puede ser nulo o estar vacio");
        }
    
    if (reservas.isEmpty() || reservas == null) {

            throw new MiException("Las reservas no pueden ser nulas o estar vacias");
        }
    
    if (comentarios.isEmpty() || comentarios == null) {

            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }
    
    
    
    }
    
}
