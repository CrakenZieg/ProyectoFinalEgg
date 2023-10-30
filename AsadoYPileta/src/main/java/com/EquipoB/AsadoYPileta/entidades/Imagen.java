
package com.EquipoB.AsadoYPileta.entidades;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Imagen {
     @Id
     @GeneratedValue (generator= "uuid")
     @GenericGenerator (name= "uuid", strategy = "uuid2")
     private String id;
     
     private String mime;
     
     private String nombre;
     @Lob @Basic(fetch = FetchType.LAZY)
     private byte[] contenido;

    
     
}
