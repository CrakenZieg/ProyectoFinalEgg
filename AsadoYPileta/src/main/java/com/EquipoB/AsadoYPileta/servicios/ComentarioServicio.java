/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Comentario;
import com.EquipoB.AsadoYPileta.entidades.Imagen;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.ComentarioRepositorio;
import com.EquipoB.AsadoYPileta.repositorios.ImagenRepositorio;

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
    @Autowired
    private ImagenServicio imagenServicio;
    

    @Transactional
    public void crearComentario(List<MultipartFile> archivos, String cuerpo) throws MiException, Exception {
        validar(cuerpo);

        Comentario comentario = new Comentario();
        comentario.setCuerpo(cuerpo);
        List<Imagen> imagenes = new ArrayList();
        Imagen imagen = new Imagen();
        for (MultipartFile archivo : archivos) {
            imagen = imagenServicio.guardar(archivo);
            imagenes.add(imagen);
        }

        comentario.setImagenes(imagenes);

        comentarioRepositorio.save(comentario);

    }

    public List<Comentario> listarComentario() {

        List<Comentario> comentarios = new ArrayList();

        comentarios = comentarioRepositorio.findAll();

        return comentarios;
    }

    public void modificarComentario(List<MultipartFile> archivos, String id, String cuerpo) throws MiException, Exception {
        validar(cuerpo);
        Optional<Comentario> respuesta = comentarioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Comentario comentario = respuesta.get();
            comentario.setCuerpo(cuerpo);
            List<Imagen> imagenes = new ArrayList();
            Imagen imagen = new Imagen();
            for (MultipartFile archivo : archivos) {
                imagen = imagenServicio.guardar(archivo);
                imagenes.add(imagen);
            }

            comentario.setImagenes(imagenes);
            comentarioRepositorio.save(comentario);
        }
    }

    public Comentario getOne(String id) {

        return comentarioRepositorio.getById(id);
    }

    private void validar(String cuerpo) throws MiException {

        if (cuerpo.isEmpty() || cuerpo == null) {
            throw new MiException("el comentario no puede ser nulo o estar vacío");
        }

    }

}
