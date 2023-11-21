
package com.EquipoB.AsadoYPileta.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class TipoPropiedad {
    
    @Id
    @GeneratedValue (generator= "uuid")
    @GenericGenerator (name= "uuid", strategy = "uuid2")
    private String id;
    private String tipo;
    private String titulo;
    private String emoji;
    private String descripcion;
    
}
