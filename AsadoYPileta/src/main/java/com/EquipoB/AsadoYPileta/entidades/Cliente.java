
package com.EquipoB.AsadoYPileta.entidades;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import lombok.Data;

@Data
@Entity
public class Cliente{

    @Id
    private String id;
    private Usuario usuario;
    private String nombre;
    private String apellido; 
    private String descripcion;
    @OneToMany
    private List<Imagen> imagenes;
    @OneToMany
    private List<Contacto> contactos;

    public Cliente() {
    }    
    
    @PrePersist
    protected void onCreate() {
        this.id = usuario.getId();
    }    
    
}
