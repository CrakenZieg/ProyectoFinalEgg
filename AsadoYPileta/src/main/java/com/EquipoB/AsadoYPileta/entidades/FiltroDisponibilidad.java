package com.EquipoB.AsadoYPileta.entidades;

import com.EquipoB.AsadoYPileta.enumeraciones.Dias;
import com.EquipoB.AsadoYPileta.enumeraciones.Meses;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class FiltroDisponibilidad {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicio;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFin;
    private int[] mensual;
    private int[] diario;
    private int[] porFecha;

    public boolean habilitado(Date inicio, Date fin) throws MiException {
        LocalDate ini = LocalDate.of(inicio.getYear() + 1900, inicio.getMonth() + 1, inicio.getDate());
        LocalDate fini = LocalDate.of(fin.getYear() + 1900, fin.getMonth() + 1, fin.getDate());
        boolean habilitado = true;
        if (fechaInicio != null) {
            if (inicio.before(fechaInicio)) {
                throw new MiException("Error: la fecha de inicio debe ser posterior a " + fechaInicio);
            }
        }
        if (fechaFin != null) {
            if (fin.after(fechaFin)) {
                throw new MiException("Error: la fecha de fin debe ser previa a " + fechaFin);
            }
        }
        if (mensual != null) {
            meses(inicio);
            meses(fin);
        }
        if (diario != null) {
            dias(ini, fini);
        }
        if (porFecha != null) {
            porFecha(ini, fini);
        }
        return habilitado;
    }

    /**
     * Toma 2 fechas, throw excepcion si en ese rango hay dias no permitidos en
     * el arreglo de int (filtro) por fecha.
     *
     * @param inicio: fecha inicial, en LocalDate para aprovechar ventajas
     * @param fin: fecha final, en LocalDate para aprovechar ventajas
     */
    public void porFecha(LocalDate inicio, LocalDate fin) throws MiException {
        if (!(porFecha[0] <= inicio.getDayOfMonth()) || !(porFecha[1] >= fin.getDayOfMonth())) {
            throw new MiException("Error: la reserva debe ser entre el " + porFecha[0] + " y el " + porFecha[1] + " de cada mes");
        }
    }

    /**
     * Toma 2 fechas, recorre el rango entre ellas y throw excepcion si en ese
     * rango hay dias no permitidos en el arreglo de int (filtro) diario.
     *
     * @param inicio: fecha inicial, en LocalDate para aprovechar ventajas
     * @param fin: fecha final, en LocalDate para aprovechar ventajas
     */
    public void dias(LocalDate inicio, LocalDate fin) throws MiException {
        List<LocalDate> fechas = dePrincipioAFin(inicio, fin);
        for (int i = 0; i < fechas.size(); i++) {
            switch (fechas.get(i).getDayOfWeek().getValue()) {                
                case 1: {
                    for (int dia : diario) {
                        if (dia == 1) {
                            break;
                        }
                    }
                    break;
                }
                case 2: {
                    for (int dia : diario) {
                        if (dia == 2) {
                            break;
                        }
                    }
                    break;
                }
                case 3: {
                    for (int dia : diario) {
                        if (dia == 3) {
                            break;
                        }
                    }
                    break;
                }
                case 4: {
                    for (int dia : diario) {
                        if (dia == 4) {
                            break;
                        }
                    }
                    break;
                }
                case 5: {
                    for (int dia : diario) {
                        if (dia == 5) {
                            break;
                        }
                    }
                    break;
                }
                case 6: {
                    for (int dia : diario) {
                        if (dia == 6) {
                            break;
                        }
                    }
                    break;
                }
                case 7: {
                    for (int dia : diario) {
                        if (dia == 7) {
                            break;
                        }
                    }
                    break;
                }
                default: {
                    throw new MiException("Error: de la reserva no puede ser " + Dias.getDia(fechas.get(i).getDayOfWeek().getValue()));
                }
            }
        }
    }

    /**
     * Devuelve una lista de LocalDate entre 2 fechas que le pases por parametro
     *
     * @param inicio: fecha inicial
     * @param fin: fecha final
     * @return lista con fecha inicial, intermedias y final.
     */
    public List<LocalDate> dePrincipioAFin(LocalDate inicio, LocalDate fin) {
        List<LocalDate> fechas = new ArrayList<>();
        long diferencia = ChronoUnit.DAYS.between(inicio, fin);
        for (int i = 0; i <= diferencia; i++) {
            LocalDate intermedio = inicio.plusDays(i);
            fechas.add(intermedio);
        }
        return fechas;
    }

    /**
     * Comprueba que la fecha ingreasada posea un mes que esté en la lista de
     * meses habilitados
     *
     * @param fecha: fecha que comprueba
     * @return throws excepcion si la fecha no esta entre las habilitadas
     */
    public void meses(Date fecha) throws MiException {
        int mes = fecha.getMonth();
        boolean excep = true;
        for (int i = 0; i < mensual.length; i++) {
            if(mensual[i] == mes){
                excep = false;
                i = mensual.length;
            }                
        }
        if(excep){
            throw new MiException("Error: de la reserva no puede ser " + Meses.getMes(mes).getMes());
        }    
    }
    

}
