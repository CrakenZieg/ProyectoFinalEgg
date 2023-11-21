package com.EquipoB.AsadoYPileta.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class Ubicacion {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String pais;
    private String provincia;
    private String departamento;
    private String localidad;
    private String calle;
    private String numeracion;
    private String observaciones;
    private Double latitud;
    private Double longitud;
    private boolean estado;
    
}
