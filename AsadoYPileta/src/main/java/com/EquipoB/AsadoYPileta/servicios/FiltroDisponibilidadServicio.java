package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.FiltroDisponibilidad;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.FiltroDisponibilidadRepositorio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FiltroDisponibilidadServicio {

    @Autowired
    public FiltroDisponibilidadRepositorio filtroDisponibilidadRepositorio;

    @Transactional
    public FiltroDisponibilidad crearFiltro(String fechaInicioReserva, String fechaFinReserva,
            int[] mensualReserva, int[] diarioReserva, int[] porFechaReserva) throws ParseException {
        FiltroDisponibilidad filtro = new FiltroDisponibilidad();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        if (fechaInicioReserva != null && !fechaInicioReserva.trim().isEmpty()) {
            Date fechaInicio = formato.parse(fechaInicioReserva);
            filtro.setFechaInicio(fechaInicio);
        }
        if (fechaFinReserva != null && !fechaFinReserva.trim().isEmpty()) {
            Date fechaFinal = formato.parse(fechaFinReserva);
            filtro.setFechaFin(fechaFinal);
        }
        if (mensualReserva != null && mensualReserva.length > 0) {
            filtro.setMensual(mensualReserva);
        }
        if (diarioReserva != null && diarioReserva.length > 0) {
            filtro.setDiario(diarioReserva);
        }
        if (porFechaReserva != null && porFechaReserva.length == 2) {
            filtro.setPorFecha(porFechaReserva);
        }
        filtroDisponibilidadRepositorio.save(filtro);
        return filtro;
    }

    @Transactional
    public FiltroDisponibilidad modificarFiltro(String id, String fechaInicioReserva, String fechaFinReserva,
            int[] mensualReserva, int[] diarioReserva, int[] porFechaReserva) throws ParseException {
        Optional<FiltroDisponibilidad> respuesta = filtroDisponibilidadRepositorio.findById(id);
        FiltroDisponibilidad filtro = new FiltroDisponibilidad();
        if (respuesta.isPresent()) {
            filtro = respuesta.get();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            if (fechaInicioReserva != null && !fechaInicioReserva.trim().isEmpty()) {
                Date fechaInicio = formato.parse(fechaInicioReserva);
                filtro.setFechaInicio(fechaInicio);
            }
            if (fechaFinReserva != null && !fechaFinReserva.trim().isEmpty()) {
                Date fechaFinal = formato.parse(fechaFinReserva);
                filtro.setFechaFin(fechaFinal);
            }
            if (mensualReserva != null && mensualReserva.length > 0) {
                filtro.setMensual(mensualReserva);
            }
            if (diarioReserva != null && diarioReserva.length > 0) {
                filtro.setDiario(diarioReserva);
            }
            if (porFechaReserva != null && porFechaReserva.length == 2) {
                filtro.setPorFecha(porFechaReserva);
            }
            filtroDisponibilidadRepositorio.save(filtro);
        }
        return filtro;
    }
    
    @Transactional(readOnly = true)
    public void eliminarFiltro(String id) throws MiException {
        Optional<FiltroDisponibilidad> respuesta = filtroDisponibilidadRepositorio.findById(id);
        if (respuesta.isPresent()) {
            FiltroDisponibilidad filtro = respuesta.get();
            filtroDisponibilidadRepositorio.delete(filtro);
        } else {
            throw new MiException("No se encontro el filtro");
        }
    }

    @Transactional(readOnly = true)
    public FiltroDisponibilidad getOne(String id) throws MiException {
        Optional<FiltroDisponibilidad> respuesta = filtroDisponibilidadRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            return new FiltroDisponibilidad();
        }
    }

}
