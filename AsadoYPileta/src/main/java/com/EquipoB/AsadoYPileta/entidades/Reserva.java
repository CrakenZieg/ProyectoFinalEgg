
package com.EquipoB.AsadoYPileta.entidades;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
    @Lob
    @Column(columnDefinition = "LONGTEXT")
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
   
    @ManyToMany
    private List<Servicio>serviciosElegidas;  
    
    @ManyToOne
    private Propiedad propiedad;
    
    @ManyToOne
    private Cliente cliente;
    
    @PrePersist
    private void onCreate(){
        this.disponible = false;
        this.montoTotal = montoTotal();
    }
    
    public double montoTotal(){
        Double monto = propiedad.getValor();
        if(serviciosElegidas!=null && !serviciosElegidas.isEmpty()){
            for (Servicio servicio : serviciosElegidas) {
                monto += monto*servicio.getValor();
            }
        }
        return monto;
    }
    
}
