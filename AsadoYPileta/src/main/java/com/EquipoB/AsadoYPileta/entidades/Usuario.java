
package com.EquipoB.AsadoYPileta.entidades;

import com.EquipoB.AsadoYPileta.enumeraciones.Rol;
import java.time.LocalDate;
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
    private String id;
    private String nombre;
    private String apellido;    
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    @Temporal(TemporalType.DATE)
    private LocalDate fechaAlta;
    
    private Boolean alta;

    public Usuario() {
    }
    
    @PrePersist
    protected void onCreate() {
        this.fechaAlta = LocalDate.now();
    }    
    
}
