
package com.EquipoB.AsadoYPileta.entidades;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Cliente extends Usuario {

    private String nombre;
    private String apellido; 
    private String descripcion;
    @OneToMany
    private List<Imagen> imagenes;
    @OneToMany
    private List<Contacto> contactos;

    public Cliente() {
    }    
    
}
