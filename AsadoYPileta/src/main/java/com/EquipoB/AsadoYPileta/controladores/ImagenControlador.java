package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.entidades.Imagen;
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

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {

    @Autowired
    private ImagenServicio imagenServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/propiedad/{id}")
    public ResponseEntity<byte[]> imagenPropiedad(@PathVariable String id) {
        Imagen imagen = imagenServicio.getOne(id);

        byte[] imagen1 = imagen.getContenido();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen1, headers, HttpStatus.OK);
    }
//<a th:if="${propiedad.imagenes != null}" th:each="imagen : ${propiedad.imagenes}"><img th:src="@{/imagen/propiedad/__${imagen.id}__}"></a>
//    @GetMapping("/propiedad/{id}")
//    public ResponseEntity <ArrayList<byte[]>> imagenesPropiedad(@PathVariable String id){
//        Propiedad propiedad = propiedadServicio.getOne(id);
//        List<Imagen> imagenes = propiedad.getImagenes();
//        ArrayList<byte[]> cont =new ArrayList();
//        for (Imagen imag : imagenes) {
//            byte[] imagen1= imag.getContenido();
//            cont.add(imagen1);
//        }
//       
//        HttpHeaders headers =new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_JPEG);
//        
//        return new ResponseEntity <>(cont,headers,HttpStatus.OK);
//    }
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
}
