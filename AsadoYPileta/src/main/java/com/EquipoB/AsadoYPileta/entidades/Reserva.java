
package com.EquipoB.AsadoYPileta.entidades;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;


@Data
@Entity
public class Reserva {
    @Id
    @GeneratedValue(generator="uuid")  
    @GenericGenerator(name= "uuid", strategy = "uuid2") 
    private String id;
   
    private String mensaje;
    private Double montoTotal;
    private Boolean disponible;
    private Boolean comentarioHabilitado;
    
    
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFin;
   
    @OneToMany
    private List<Servicio>serviciosElegidas;  
    
    @ManyToOne
    private Propiedad propiedad;
    
    @ManyToOne
    private Cliente cliente;
    
    @PrePersist
    private void onCreate(){
        this.disponible = false;
    }
    
}
