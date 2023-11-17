package com.EquipoB.AsadoYPileta.entidades;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Comentario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String cuerpo;
    @OneToMany
    private List<Imagen> imagenes;

    @ManyToOne
    private Propiedad propiedad;
    @ManyToOne
    private Cliente cliente;
    @OneToOne
    private Reserva Reserva;

    private double puntuacion;
    
}
