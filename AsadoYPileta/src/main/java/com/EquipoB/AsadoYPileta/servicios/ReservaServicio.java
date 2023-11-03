package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Reserva;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.ReservaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservaServicio {

    @Autowired
    private ReservaRepositorio reservaRepositorio;

    @Transactional
    public void crearReserva(String id, String mensaje, Date fechaInicio, Date fechaFin, List serviciosElegidas, Double montoTotal, Boolean disponible) throws MiException {

        validar(mensaje, fechaInicio, fechaFin, disponible);

        Reserva reserva = new Reserva();

        reserva.setId(id);
        reserva.setMensaje(mensaje);
        reserva.setFechaInicio(fechaInicio);
        reserva.setFechaFin(fechaFin);
        reserva.setServiciosElegidas(serviciosElegidas);
        reserva.setMontoTotal(montoTotal);
        reserva.setDisponible(disponible);

        reservaRepositorio.save(reserva);

    }

    public List<Reserva> listarReserva() {

        List<Reserva> reservas = new ArrayList();

        reservas = reservaRepositorio.findAll();

        return reservas;
    }

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
    
    public Reserva getOne(String id){
        
        return reservaRepositorio.getOne(id);
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

    private void validar(String mensaje, Date fechaInicio, Date fechaFin, Boolean disponible) throws MiException {

        if (mensaje.isEmpty() || mensaje == null) {

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
