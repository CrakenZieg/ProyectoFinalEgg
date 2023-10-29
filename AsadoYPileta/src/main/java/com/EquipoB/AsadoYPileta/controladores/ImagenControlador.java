/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.entidades.Imagen;
import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.servicios.ImagenServicio;
import com.EquipoB.AsadoYPileta.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 *
 * @author Tamara
 */
@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
    @Autowired
    private ImagenServicio imagenServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    
    @GetMapping("/{id}")//<a th:if="${propiedad.imagenes != null}" th:each="imagen : ${propiedad.imagenes}"><img th:src="@{/imagen/__${imagen.id}__}"></a>
    public ResponseEntity<byte[]> imagenPropiedad(@PathVariable String id){
        Imagen img = imagenServicio.getOne(id);
        byte[] imagen = img.getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity <>(imagen,headers,HttpStatus.OK);
    }
    
//    @GetMapping("/perfil/{id}")
//    public ResponseEntity <byte[]> imagenUsuario(@PathVariable String id){
//        Usuario usuario= usuarioServicio.getOne(id);
//        byte[] imagen=usuario.getImagen().getContenido();
//        HttpHeaders headers =new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_JPEG);
//        
//        return new ResponseEntity <>(imagen,headers,HttpStatus.OK);
//    }
//    
//    @GetMapping("/comentario/{id}")
//    public ResponseEntity <byte[]> imagenComentario(@PathVariable String id){
//        Comentario comentario= comentarioServicio.getOne(id);
//        byte[] imagen=usuario.getImagen().getContenido();
//        HttpHeaders headers =new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_JPEG);
//        
//        return new ResponseEntity <>(imagen,headers,HttpStatus.OK);
//        
//    }
}
