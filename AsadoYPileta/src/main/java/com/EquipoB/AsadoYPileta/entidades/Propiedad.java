
package com.EquipoB.AsadoYPileta.entidades;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Propiedad {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String titulo;
    private String descripcion;
    private Boolean estado;
    private Double valor;
    @ManyToOne
    private TipoPropiedad tipo;   
    @ManyToMany
    private List<Servicio> servicios;
    @OneToMany
    private List<Imagen> imagenes;
    @OneToOne(cascade = CascadeType.ALL)
    private Ubicacion ubicacion;

    private Double puntuacion;

    @OneToOne(cascade = CascadeType.ALL)
    private FiltroDisponibilidad filtroDisponibilidad;

}
