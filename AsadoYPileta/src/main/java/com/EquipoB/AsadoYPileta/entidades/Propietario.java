package com.EquipoB.AsadoYPileta.entidades;

import java.util.List;
import javax.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Propietario extends Usuario {

    private List<Propiedad> propiedades;
}
