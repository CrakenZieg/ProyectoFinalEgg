
package com.EquipoB.AsadoYPileta.entidades;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Data
public class Reserva {
    @Id
    @GeneratedValue(generator="uuid")  
    @GenericGenerator(name= "uuid", strategy = "uuid2") 
    private String id;
   
    private String mensaje;
    
    @Temporal(TemporalType.DATE)
    private LocalDate fechaInicio;
    @Temporal(TemporalType.DATE)
    private LocalDate fechaFin;
   
    
    private List <Servicio>serviciosElegidas;
    private Double montoTotal;
    private Boolean disponible;
    
    
}
