package com.EquipoB.AsadoYPileta.entidades;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import lombok.Data;

@Entity
@Data
public class Propietario{

    @Id
    private String id;
    private Cliente cliente;
    @OneToMany
    private List<Propiedad> propiedades;
    
    @PrePersist
    protected void onCreate() {
        this.id = cliente.getId();
    }   
    
}
