/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EquipoB.AsadoYPileta.entidades;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Tamara
 */
@Entity
public class Imagen {
     @Id
     @GeneratedValue (generator= "uuid")
     @GenericGenerator (name= "uuid", strategy = "uuid2")
     private String id;
     
     private String mime;
     
     private String nombre;
     @Lob @Basic(fetch = FetchType.LAZY)
     private byte[] contenido;

    public Imagen() {
    }

    public Imagen(String id, String mime, String nombre, byte[] contenido) {
        this.id = id;
        this.mime = mime;
        this.nombre = nombre;
        this.contenido = contenido;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }
     
}
