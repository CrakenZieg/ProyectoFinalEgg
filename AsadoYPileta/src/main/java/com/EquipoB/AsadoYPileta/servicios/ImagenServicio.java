
package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Imagen;
import com.EquipoB.AsadoYPileta.repositorios.ImagenRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Tamara
 */
@Service
public class ImagenServicio {
     @Autowired
    private ImagenRepositorio imagenRepositorio;
    
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
}
