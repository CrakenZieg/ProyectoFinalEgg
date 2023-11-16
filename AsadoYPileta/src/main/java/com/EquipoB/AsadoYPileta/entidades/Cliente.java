
package com.EquipoB.AsadoYPileta.entidades;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import lombok.Data;

@Data
@Entity
public class Cliente{


    @Id
    private String id;
    @OneToOne(cascade = CascadeType.ALL)
    private Usuario usuario;
    private String nombre;
    private String apellido; 
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Imagen> imagenes;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Contacto> contactos;
      
    

    @PrePersist
    private void onCreate() {
        this.id = usuario.getId();
    }    
    
}
