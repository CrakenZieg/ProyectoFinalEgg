
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

    public Propiedad() {
    }

    public Propiedad(String id, String titulo, String descripcion, TipoPropiedad tipo, Double valor, List<Servicio> servicios, List<Imagen> imagenes, Boolean estado,Ubicacion ubicacion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.valor = valor;
        this.servicios = servicios;
        this.imagenes = imagenes;
        this.estado = estado;
        this.ubicacion =ubicacion;
    }

 
    
    
}
