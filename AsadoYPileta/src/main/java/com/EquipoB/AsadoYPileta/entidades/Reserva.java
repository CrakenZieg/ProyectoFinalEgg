
package com.EquipoB.AsadoYPileta.entidades;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    private Double montoTotal;
    private Boolean disponible;
    
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
   
    @OneToMany
    private List<Servicio>serviciosElegidas;    
    
}
