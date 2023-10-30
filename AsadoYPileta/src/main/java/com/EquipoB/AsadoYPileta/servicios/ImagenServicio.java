package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Imagen;
import com.EquipoB.AsadoYPileta.repositorios.ImagenRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepositorio imagenRepositorio;

    @Transactional
    public Imagen guardar(MultipartFile archivo) throws Exception {
        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getOriginalFilename());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    @Transactional
    public List<Imagen> guardarVarias(MultipartFile[] imagenInput) throws Exception {
        if (imagenInput != null) {
            List<Imagen> imagenes = new ArrayList<>();
            try {
                for (MultipartFile imagenElem : imagenInput) {
                    Imagen imagen = new Imagen();
                    imagen.setMime(imagenElem.getContentType());
                    imagen.setNombre(imagenElem.getOriginalFilename());
                    imagen.setContenido(imagenElem.getBytes());
                    imagenes.add(imagenRepositorio.save(imagen));
                }
                return imagenes;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    @Transactional
    public Imagen actualizar(MultipartFile archivo, String id) throws Exception {
        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();
                if (id != null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(id);

                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }

                }

                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getOriginalFilename());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Imagen getOne(String id) {

        return imagenRepositorio.getOne(id);

    }
    
    public List<Imagen> filtrar(List<Imagen> imagenesRepo, String[] imagenesViejas){
        for (String imagenVieja : imagenesViejas) {
            Optional<Imagen> respuesta = imagenRepositorio.findById(imagenVieja);
            if (respuesta.isPresent()) {
                Imagen imagen = respuesta.get();
                if(imagenesRepo.contains(imagen)){
                    imagenesRepo.remove(imagen);
                }
                borrar(imagen.getId());
            }
        }
        return imagenesRepo;        
    }

    @Transactional
    public void borrar(String id) {
        try {
            Optional<Imagen> respuesta = imagenRepositorio.findById(id);

            if (respuesta.isPresent()) {
                imagenRepositorio.deleteById(id);
            }

        } catch (Exception e) {
            e.getMessage();
        }
    }

}
