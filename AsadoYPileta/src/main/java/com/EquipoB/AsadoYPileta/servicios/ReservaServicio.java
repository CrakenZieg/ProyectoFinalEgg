package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.Reserva;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.PropiedadRepositorio;
import com.EquipoB.AsadoYPileta.repositorios.ReservaRepositorio;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaServicio {

    @Autowired
    private ReservaRepositorio reservaRepositorio;
    @Autowired
    private PropiedadRepositorio propiedadRepositorio;

    @Transactional
    public void crearReserva(String idPropiedad, String mensaje, Date fechaInicio, Date fechaFin, 
            List serviciosElegidas) throws MiException {

        validar(mensaje, fechaInicio, fechaFin);
        Reserva reserva = new Reserva();
        reserva.setPropiedad(propiedadRepositorio.getById(idPropiedad));
        reserva.setMensaje(mensaje);
        reserva.setFechaInicio(fechaInicio);
        reserva.setFechaFin(fechaFin);
        reserva.setServiciosElegidas(serviciosElegidas);

        reserva.setMontoTotal(reserva.getMontoTotal());

        reservaRepositorio.save(reserva);

    }

    @Transactional(readOnly = true) 
    public List<Reserva> listarReservaCliente(String id) {


        List<Reserva> reservas = new ArrayList();

        reservas = reservaRepositorio.buscarReservaPorCliente(id);

        return reservas;
    }
    
    @Transactional(readOnly = true) 
    public List<Reserva> listarReservaPropiedadEnPerfil(List<Propiedad> propiedades) {
        List <String> idPropiedades = new ArrayList();
        for (Propiedad propiedad : propiedades) {
            String idPropiedad = propiedad.getId();
            idPropiedades.add(idPropiedad);
        }


        List<Reserva> reservas = new ArrayList();

        reservas = reservaRepositorio.buscarReservaPorPropiedadPerfil(idPropiedades);

        return reservas;
    }

    @Transactional
    public void modificarReserva(String id, String mensaje, Date fechaInicio, Date fechaFin, List serviciosElegidas) throws MiException {

        validar(mensaje, fechaInicio, fechaFin);

        Optional<Reserva> respuesta = reservaRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Reserva reserva = respuesta.get();
            reserva.getPropiedad().getFiltroDisponibilidad().habilitado(fechaInicio, fechaFin);
            reserva.setMensaje(mensaje);
            reserva.setFechaInicio(fechaInicio);
            reserva.setFechaFin(fechaFin);
            reserva.setServiciosElegidas(serviciosElegidas);
            reserva.setMontoTotal(reserva.montoTotal());
            reservaRepositorio.save(reserva);
        }

    }

    @Transactional(readOnly = true)
    public Reserva getOne(String id) {

        return reservaRepositorio.getOne(id);
    }

    @Transactional
    public boolean validarReservasCliente(String id) {
        return reservaRepositorio.buscarReservaCliente(id);
    }

    @Transactional
    public boolean validarReservasPropiedad(String id) {
        return reservaRepositorio.buscarReservaPropiedad(id);
    }

    @Transactional
    public void borrar(String id) {

        try {
            Optional<Reserva> respuesta = reservaRepositorio.findById(id);

            if (respuesta.isPresent()) {
                reservaRepositorio.deleteById(id);
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public boolean verificarSuperposicionReservas(String idPropiedad, Date fechaInicio, Date fechaFin) {

        List<Date> fechasInicioReservas = reservaRepositorio.buscarFechaInicioReserva(idPropiedad);
        List<Date> fechasFinReservas = reservaRepositorio.buscarFechaFinReserva(idPropiedad);

        for (int i = 0; i < fechasInicioReservas.size(); i++) {
            Date inicioReserva = fechasInicioReservas.get(i);
            Date finReserva = fechasFinReservas.get(i);

            if ((inicioReserva.before(fechaFin) || inicioReserva.equals(fechaFin))
                    && (finReserva.after(fechaInicio) || finReserva.equals(fechaInicio))) {

                return false;
            }
        }

        return true;
    }

    public List<Reserva> reservasFuturas(String id) {
        List<Reserva> reservas = new ArrayList();
        reservas = reservaRepositorio.buscarReservaPorPropiedad(id);
        return reservas;
    }

    @Transactional
    public void habilitarComentarioFinReserva(Date fechaFin) {

        List<Reserva> finReserva = reservaRepositorio.buscarFinReserva(fechaFin);

        for (Reserva reserva : finReserva) {
            reserva.setComentarioHabilitado(true);
            reservaRepositorio.save(reserva);

        }
    }

    //se ejecuta todos los dias a las 00:00 hs
    @Scheduled(cron = "0 0 0 ? * * ")
    public void bajaAutomatica() {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = formato.format(new Date());

        List<Reserva> finalizadas = reservaRepositorio.buscarFinalizadas(fecha);

        if (!finalizadas.isEmpty()) {
            for (Reserva finalizada : finalizadas) {
                Reserva modificar = finalizada;
                modificar.setDisponible(Boolean.FALSE);
                modificar.setComentarioHabilitado(Boolean.TRUE);
                reservaRepositorio.save(modificar);
            }
        }

    }
    
    @Transactional
    public void aceptarReserva(String id){
        Reserva reserva = reservaRepositorio.getById(id);
        reserva.setDisponible(true);
        reservaRepositorio.save(reserva);
    }

    private void validar(String mensaje, Date fechaInicio, Date fechaFin) throws MiException {

        if (mensaje == null || mensaje.trim().isEmpty()) {

            throw new MiException("El mensaje no puede estar vacio, tiene que ingresar un mensaje");
        }

        if (fechaInicio == null || fechaFin == null) {

            throw new MiException("La fechas de reserva no pueden ser nulas ");
        }
        if (fechaInicio.before(fechaFin)) {

            throw new MiException("La fecha de Inicio no puede ser posterior a la fecha de Fin");
        }
    }
}
