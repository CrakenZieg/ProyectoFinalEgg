package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.Reserva;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.PropiedadRepositorio;
import com.EquipoB.AsadoYPileta.repositorios.ReservaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaServicio {

    @Autowired
    private ReservaRepositorio reservaRepositorio;
    @Autowired
    private PropiedadRepositorio propiedadRepositorio;

    @Transactional
    public void crearReserva(String mensaje, Date fechaInicio, Date fechaFin, List serviciosElegidas, Double montoTotal, Boolean disponible) throws MiException {

        validar(mensaje, fechaInicio, fechaFin, disponible);

        Reserva reserva = new Reserva();

        reserva.setMensaje(mensaje);
        reserva.setFechaInicio(fechaInicio);
        reserva.setFechaFin(fechaFin);
        reserva.setServiciosElegidas(serviciosElegidas);
        reserva.setMontoTotal(montoTotal);
        reserva.setDisponible(disponible);

        reservaRepositorio.save(reserva);

    }
    
    @Transactional(readOnly = true) 
    public List<Reserva> listarReserva() {

        List<Reserva> reservas = new ArrayList();

        reservas = reservaRepositorio.findAll();

        return reservas;
    }
    @Transactional
    public void modificarReserva(String id, String mensaje, Date fechaInicio, Date fechaFin, List serviciosElegidas, Double montoTotal, Boolean disponible) throws MiException {

        validar(mensaje, fechaInicio, fechaFin, disponible);
        
        Optional<Reserva> respuesta = reservaRepositorio.findById(id);
        
     

        if (respuesta.isPresent()) {

            Reserva reserva = respuesta.get();

            reserva.setMensaje(mensaje);
            reserva.setFechaInicio(fechaInicio);
            reserva.setFechaFin(fechaFin);
            reserva.setServiciosElegidas(serviciosElegidas);

            reservaRepositorio.save(reserva);
        }

    }
    
    @Transactional(readOnly = true)
    public Reserva getOne(String id){
        
        return reservaRepositorio.getOne(id);
    }
    
    @Transactional
    public boolean validarReservasCliente(String id){
        return reservaRepositorio.buscarReservaCliente(id);
    }
    
    @Transactional
    public boolean validarReservasPropiedad(String id){
        return reservaRepositorio.buscarReservaPropiedad(id);
    }
    
    @Transactional
    public void borrar(String id){
        
        try {
            Optional <Reserva> respuesta = reservaRepositorio.findById(id);
            
            if(respuesta.isPresent()){
                reservaRepositorio.deleteById(id);
            }
        } catch(Exception e){
            e.getMessage();
        }
        
    }
    
    public List<Reserva> listarFechasReservasDePropiedad(String id){
        Reserva reserva = new Reserva();
        Optional <Propiedad> respuesta = propiedadRepositorio.findById(id);
        
       Propiedad propiedadReservada = respuesta.get();
        
        reserva.setPropiedad(propiedadReservada);
        
        List<Date> fechasOcupadas = new ArrayList();
        
       
        fechasOcupadas = reserva.getPropiedad().g
    }
    
    public void verificarOcupado(String id, Date fechaInicio, Date fechaFin){
        
        Optional <Propiedad> respuesta = propiedadRepositorio.findById(id);
        
        Propiedad propiedad = respuesta.get();
        
        Reserva reserva = new Reserva();
        
        
            
    }

    private void validar(String mensaje, Date fechaInicio, Date fechaFin, Boolean disponible) throws MiException {

        if (mensaje == null || mensaje.trim().isEmpty() ) {

            throw new MiException("El mensaje no puede estar vacio, tiene que ingresar un mensaje");
        }

        if (fechaInicio == null || fechaFin == null) {

            throw new MiException("La fechas de reserva no pueden ser nulas ");
        }
        if (fechaInicio.before(fechaFin)) {

            throw new MiException("La fecha de Inicio no puede ser posterior a la fecha de Fin");
        }

        if (disponible == false) {

            throw new MiException("La propiedad que quiere reservar no esta disponible");
        }
    }
}
