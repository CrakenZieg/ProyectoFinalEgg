/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.entidades.Imagen;
import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.servicios.ImagenServicio;
import com.EquipoB.AsadoYPileta.servicios.PropiedadServicio;
import com.EquipoB.AsadoYPileta.servicios.UsuarioServicio;
import java.util.List;
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
    private PropiedadServicio propiedadServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    
    @GetMapping("/propiedad/{id}")
    public ResponseEntity<byte[]> imagenPropiedad(@PathVariable String id){
        Propiedad propiedad= propiedadServicio.getOne(id);
        byte[] imagen=propiedad.getImagenes().getContenido();
        HttpHeaders headers =new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity <>(imagen1,headers,HttpStatus.OK);
    }
    
    
    @GetMapping("/propiedad/{id}") //<a><img th:if="${propiedad.imagen != null}" th:src="@{/imagen/propiedad/__${propiedad.id}__}/__${imagen.id}__}"></a>
    public ResponseEntity <ArrayList<byte[]>> imagenesPropiedad(@PathVariable String id){
        Propiedad propiedad = propiedadServicio.getOne(id);
        List<Imagen> imagenes = propiedad.getImagenes();
        ArrayList<byte[]> cont =new ArrayList();
        for (Imagen imag : imagenes) {
            byte[] imagen1= imag.getContenido();
            cont.add(imagen1);
        }
       
        HttpHeaders headers =new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity <>(cont,headers,HttpStatus.OK);
    }
    
    @GetMapping("/perfil/{id}")
    public ResponseEntity <byte[]> imagenUsuario(@PathVariable String id){
        Usuario usuario= usuarioServicio.getOne(id);
        byte[] imagen=usuario.getImagen().getContenido();
        HttpHeaders headers =new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity <>(imagen,headers,HttpStatus.OK);
    }
    
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
