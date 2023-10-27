/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Comentario;
import com.EquipoB.AsadoYPileta.entidades.Imagen;
import com.EquipoB.AsadoYPileta.repositorios.ComentarioRepositorio;
import com.EquipoB.AsadoYPileta.repositorios.ImagenRepositorio;
import com.egg.news.excepciones.MiException;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Gabriela Rivadero
 */
@Service
public class ComentarioServicio {

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;
    @Autowired
    private ImagenRepositorio imagenRepositorio;

    @Transactional
    public void crearComentario(MultipartFile archivo,String cuerpo, List<Imagen> imagenes) throws MiException {
        validar(cuerpo, imagenes);

        Comentario comentario = new Comentario();
        comentario.setCuerpo(cuerpo);
        comentario.setImagenes(imagenes);

        comentarioRepositorio.save(comentario);

    }

    public List<Comentario> listarComentario() {

        List<Comentario> comentarios = new ArrayList();

        comentarios = comentarioRepositorio.findAll();

        return comentarios;
    }

    public void modificarComentario(MultipartFile archivo,String id, String cuerpo, List<Imagen> imagenes) throws MiException {
        validar(cuerpo, imagenes);
        Optional<Comentario> respuesta = comentarioRepositorio.findById(id);
       
        if (respuesta.isPresent()) {

            Comentario comentario = respuesta.get();
            comentario.setCuerpo(cuerpo);
            comentario.setImagenes(imagenes);
            comentarioRepositorio.save(comentario);
        }
    }

    public Comentario getOne(String id) {

        return comentarioRepositorio.getOne(id);
    }

     private void validar(String cuerpo,List<Imagen> imagenes) throws MiException {

        if (cuerpo.isEmpty() || cuerpo == null) {
            throw new MiException("el comentario no puede ser nulo o estar vacío");
        }
        if (imagenes.isEmpty() || imagenes == null) {
            throw new MiException("No puede no cargar imagenes");
        }
        
        }

}
