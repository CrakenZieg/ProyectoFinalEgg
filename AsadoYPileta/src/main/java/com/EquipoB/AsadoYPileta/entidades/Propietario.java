package com.EquipoB.AsadoYPileta.entidades;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Propietario extends Usuario {

    @OneToMany
    private List<Propiedad> propiedades;
}
