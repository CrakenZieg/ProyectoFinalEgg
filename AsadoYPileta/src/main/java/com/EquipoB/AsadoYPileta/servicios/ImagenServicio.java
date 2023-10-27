
package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Imagen;

import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import java.util.List;

import com.EquipoB.AsadoYPileta.repositorios.ImagenRepositorio;
import com.EquipoB.AsadoYPileta.repositorios.PropiedadRepositorio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Tamara
 */
@Service
public class ImagenServicio {
     @Autowired
    private ImagenRepositorio imagenRepositorio;
     
     @Autowired
     private PropiedadRepositorio propiedadRepositorio;
    
     @Transactional
    public Imagen guardar (MultipartFile archivo) throws Exception{
        if(archivo != null){
            try{
                Imagen imagen =new Imagen ();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
    @Transactional
    public Imagen actualizar (MultipartFile archivo, String id) throws Exception{
          if(archivo != null){
            try{
                Imagen imagen =new Imagen ();
                if (id !=null){
                    Optional <Imagen> respuesta = imagenRepositorio.findById(id);
                    
                    if (respuesta.isPresent()){
                        imagen =respuesta.get();
                    }
                    
                }
                
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
   @Transactional(readOnly = true)
    public Imagen getOne(String id) {

        return imagenRepositorio.getOne(id);
    
    }
    
     @Transactional(readOnly = true)
    public List<Imagen> listarImagenesPropiedad (String id){
        Propiedad propiedad = propiedadRepositorio.findById(id);
        
        List<Imagen> listaImagenes = propiedad.getImagenes();
        
       
        return listaImagenes;
    }
    
  
    
    
}
