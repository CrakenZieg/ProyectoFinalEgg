
package com.EquipoB.AsadoYPileta.entidades;

import com.EquipoB.AsadoYPileta.enumeraciones.TipoPropiedad;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    private TipoPropiedad tipo;    
    private Double valor;
    @OneToMany
    private List<Servicio> servicios;
    @OneToMany
    private List<Imagen> imagenes;
    @OneToMany
    private List<Reserva> reservas;
    @OneToMany
    private List<Comentario> comentarios;
}
