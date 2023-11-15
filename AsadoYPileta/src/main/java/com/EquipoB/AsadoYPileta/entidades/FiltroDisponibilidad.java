package com.EquipoB.AsadoYPileta.entidades;

import com.EquipoB.AsadoYPileta.enumeraciones.Meses;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import java.util.Date;
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
    private String[] mensual;
    private String[] diario;

    public boolean habilitado(Date inicio, Date fin) throws MiException {
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
//        if (diario != null){
//            if(diaI<diaF){
//                for(int i = diaI; i <= diaF; i++){
//                    
//                }
//            }
//        }

        return habilitado;

    }
    
    public void dias(Date inicio, Date fin){      
        
        int diaI = inicio.getDay();
        int diaF = fin.getDay();
        if(diaI>diaF){} else{        
            for (int i = diaI; i <= diaF; i++) {
//                switch (diario[i]) {
//                    case "DOMINGO": {
//                        if (mes == 0) {
//                            break;
//                        }
//                    }
//                    case "LUNES": {
//                        if (mes == 1) {
//                            break;
//                        }
//                    }
//                    case "MARTES": {
//                        if (mes == 2) {
//                            break;
//                        }
//                    }
//                    case "MIERCOLES": {
//                        if (mes == 3) {
//                            break;
//                        }
//                    }
//                    case "JUEVES": {
//                        if (mes == 4) {
//                            break;
//                        }
//                    }
//                    case "VIERNES": {
//                        if (mes == 5) {
//                            break;
//                        }
//                    }
//                    case "SABADO": {
//                        if (mes == 6) {
//                            break;
//                        }                    
//                    }
//                    default: {
//                        throw new MiException("Error: de la reserva no puede ser " + Meses.getMes(mes).getMes());
//                    }
//                }
            }
        }
    }

    public void meses(Date fecha) throws MiException {
        int mes = fecha.getMonth();
        for (int i = 0; i < mensual.length; i++) {
            switch (mensual[i]) {
                case "ENERO": {
                    if (mes == 0) {
                        break;
                    }
                }
                case "FEBRERO": {
                    if (mes == 1) {
                        break;
                    }
                }
                case "MARZO": {
                    if (mes == 2) {
                        break;
                    }
                }
                case "ABRIL": {
                    if (mes == 3) {
                        break;
                    }
                }
                case "MAYO": {
                    if (mes == 4) {
                        break;
                    }
                }
                case "JUNIO": {
                    if (mes == 5) {
                        break;
                    }
                }
                case "JULIO": {
                    if (mes == 6) {
                        break;
                    }
                }
                case "AGOSTO": {
                    if (mes == 7) {
                        break;
                    }
                }
                case "SEPTIEMBRE": {
                    if (mes == 8) {
                        break;
                    }
                }
                case "OCTUBRE": {
                    if (mes == 9) {
                        break;
                    }
                }
                case "NOVIEMBRE": {
                    if (mes == 10) {
                        break;
                    }
                }
                case "DICIEBRE": {
                    if (mes == 11) {
                        break;
                    }
                }
                default: {
                    throw new MiException("Error: de la reserva no puede ser " + Meses.getMes(mes).getMes());
                }
            }
        }
    }

}
