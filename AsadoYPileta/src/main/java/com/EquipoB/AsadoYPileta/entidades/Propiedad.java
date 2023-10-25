
package com.EquipoB.AsadoYPileta.entidades;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
    private String ubicacion;
    private String direccion;
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    @ManyToOne
    private List<Servicio> servicios;
    private Double valor;
    private List<Reserva> reservas;
    private List<Comentario> comentarioervas;
}
