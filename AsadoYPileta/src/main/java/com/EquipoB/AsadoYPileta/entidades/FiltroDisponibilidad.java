
package com.EquipoB.AsadoYPileta.entidades;

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
    
    public boolean habilitado(Date inicio, Date fin) throws MiException{
        boolean habilitado = true;
        if(fechaInicio!=null){
            if(inicio.before(fechaInicio)){
                throw new MiException("Error: la fecha de inicio debe ser posterior a "+fechaInicio);
            }
        }
        if(fechaFin!=null){
            if(fin.after(fechaFin)){
                throw new MiException("Error: la fecha de fin debe ser previa a "+fechaFin);
            }
        }
        int mesI = inicio.getMonth();
        int mesF = fin.getMonth();
        
        
        
        
        return habilitado;        
        
        
    }
    
}
