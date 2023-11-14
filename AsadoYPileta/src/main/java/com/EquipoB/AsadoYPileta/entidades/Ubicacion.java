/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EquipoB.AsadoYPileta.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Tamara
 */
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
