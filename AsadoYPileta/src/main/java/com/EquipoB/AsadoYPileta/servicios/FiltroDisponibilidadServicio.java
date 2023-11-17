package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.FiltroDisponibilidad;
import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.FiltroDisponibilidadRepositorio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
            String[] mensualReserva, String[] diarioReserva, String[] porFechaReserva) throws ParseException {
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
            filtro.setMensual(parsearArregloString(mensualReserva));
        }
        if (diarioReserva != null && diarioReserva.length > 0) {
            filtro.setDiario(parsearArregloString(diarioReserva));
        }
        if (porFechaReserva != null && porFechaReserva.length == 2) {
            filtro.setPorFecha(parsearArregloString(porFechaReserva));
        }
        filtroDisponibilidadRepositorio.save(filtro);
        return filtro;
    }

    @Transactional
    public FiltroDisponibilidad modificarFiltro(String id, String fechaInicioReserva, String fechaFinReserva,
            String[] mensualReserva, String[] diarioReserva, String[] porFechaReserva) throws ParseException {
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
                filtro.setMensual(parsearArregloString(mensualReserva));
            }
            if (diarioReserva != null && diarioReserva.length > 0) {
                filtro.setDiario(parsearArregloString(diarioReserva));
            }
            if (porFechaReserva != null && porFechaReserva.length == 2) {
                filtro.setPorFecha(parsearArregloString(porFechaReserva));
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

    public int[] parsearArregloString(String[] arreglo) {
        if (arreglo != null) {
            int[] enteros = new int[arreglo.length];
            for (int i = 0; i < arreglo.length; i++) {
                enteros[i] = Integer.parseInt(arreglo[i]);
            }
            return enteros;
        }
        return null;
    }

    public List<String> obtenerDiasHabilitados(FiltroDisponibilidad filtro) {
        int[] arregloNuevo = null;
        if (filtro.getDiario() != null) {
            arregloNuevo = filtro.getDiario().clone();
            for (int i = 0; i < filtro.getDiario().length; i++) {
                arregloNuevo[i] = arregloNuevo[i] + 1;
                if (arregloNuevo[i] == 8) {
                    arregloNuevo[i] = 1;
                }
            }
        }

        List<String> diasHabilitados = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        if (filtro.getFechaInicio() != null) {
            calendar.setTime(filtro.getFechaInicio());
        } else {
            calendar.setTime(new Date());
        }
        Calendar fechaFinCalendar = Calendar.getInstance();
        fechaFinCalendar.setTime(añoSiguiente(filtro.getFechaFin()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        while (calendar.before(fechaFinCalendar) || calendar.equals(fechaFinCalendar)) {
            int mesActual = calendar.get(Calendar.MONTH);
            int diaSemanaActual = calendar.get(Calendar.DAY_OF_WEEK);
            if (contiene(mesesDisponible(filtro.getMensual()), mesActual)) {
                if (contiene(diasDisponibles(filtro.getDiario()), diaSemanaActual)) {
                    diasHabilitados.add(dateFormat.format(calendar.getTime()));
                }
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return diasHabilitados;
    }

    private boolean contiene(int[] array, int valor) {
        if (array != null) {
            for (int elemento : array) {
                if (elemento == valor) {
                    return true;
                }
            }
        }
        return false;
    }

    public int[] diasDisponibles(int[] dias) {
        if (dias != null) {
            int[] arregloNuevoDias = dias.clone();
            for (int i = 0; i < dias.length; i++) {
                arregloNuevoDias[i] = arregloNuevoDias[i] + 1;
                if (arregloNuevoDias[i] == 8) {
                    arregloNuevoDias[i] = 1;
                }
            }
            return arregloNuevoDias;
        } else {
            return new int[]{1, 2, 3, 4, 5, 6, 7};
        }
    }

    public int[] mesesDisponible(int[] meses) {
        if (meses == null) {
            return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        } else {
            return meses;
        }

    }

    public Date añoSiguiente(Date diaFin) {
        if (diaFin != null) {
            return diaFin;
        } else {
            Date fechaHoy = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaHoy);
            calendar.add(Calendar.YEAR, 1);
            return calendar.getTime();
        }
    }

    public List<Integer> listaDeEnterosDiasReservados(Propiedad propiedad) {
        List<Integer> respuesta = new ArrayList<>();
        int[] arregloInt = propiedad.getFiltroDisponibilidad().getDiario();
        for (int i = 0; i < arregloInt.length; i++) {
            respuesta.add(arregloInt[i]);
        }
        return respuesta;
    }

}
