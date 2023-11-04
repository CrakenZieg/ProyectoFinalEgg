
package com.EquipoB.AsadoYPileta.entidades;

import com.EquipoB.AsadoYPileta.enumeraciones.Rol;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Usuario {
    
    @Id
    @GeneratedValue (generator= "uuid")
    @GenericGenerator (name= "uuid", strategy = "uuid2")
    protected String id;   
    protected String email;
    protected String password;
    @Enumerated(EnumType.STRING)
    protected Rol rol;
    @Temporal(TemporalType.DATE)
    protected Date fechaAlta;
    
    protected Boolean alta;

    public Usuario() {
    }
    
    @PrePersist
    protected void onCreate() {
        this.fechaAlta = new Date();
        this.alta = true;
    }    
    
}
