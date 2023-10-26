
package com.EquipoB.AsadoYPileta.entidades;

import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;


@Entity
public class Comentario {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
     private String comentario;
     private ArrayList <Imagen> imagen;
   
    public Comentario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public ArrayList<Imagen> getImagen() {
        return imagen;
    }

    public void setImagen(ArrayList<Imagen> imagen) {
        this.imagen = imagen;
    }

 
}
