
package com.EquipoB.AsadoYPileta.entidades;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;

@Entity
public class Cliente extends Usuario {

    private String nombre;
    private String apellido;
    private ArrayList<Imagen> imagenes;
    private String descripcion;
    private List<String> contactoTel;
    private List<String> contactoEmail;
    private List<String> contactoRedes;
    private List<Contacto> contactos;

    public Cliente() {
    }
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public ArrayList<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(ArrayList<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<String> getContactoTel() {
        return contactoTel;
    }

    public void setContactoTel(List<String> contactoTel) {
        this.contactoTel = contactoTel;
    }

    public List<String> getContactoEmail() {
        return contactoEmail;
    }

    public void setContactoEmail(List<String> contactoEmail) {
        this.contactoEmail = contactoEmail;
    }

    public List<String> getContactoRedes() {
        return contactoRedes;
    }

    public void setContactoRedes(List<String> contactoRedes) {
        this.contactoRedes = contactoRedes;
    }

    public List<Contacto> getContactos() {
        return contactos;
    }

    public void setContactos(List<Contacto> contactos) {
        this.contactos = contactos;
    }
    
    
    
    
    
}
