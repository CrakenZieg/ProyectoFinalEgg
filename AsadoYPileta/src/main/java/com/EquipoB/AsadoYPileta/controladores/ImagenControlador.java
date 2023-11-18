package com.EquipoB.AsadoYPileta.controladores;
import com.EquipoB.AsadoYPileta.entidades.Cliente;
import com.EquipoB.AsadoYPileta.entidades.Imagen;
import com.EquipoB.AsadoYPileta.servicios.ClienteServicio;
import com.EquipoB.AsadoYPileta.servicios.ImagenServicio;
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
    private ClienteServicio clienteServicio;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> imagen(@PathVariable String id) {
        Imagen imagen = imagenServicio.getOne(id);
        byte[] imagen1 = imagen.getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(imagen.getMime()));
        return new ResponseEntity<>(imagen1, headers, HttpStatus.OK);
    }
    
    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenPerfil(@PathVariable String id) {
        Cliente cliente = clienteServicio.getOne(id);
        byte[] imagen = cliente.getImagenes().get(0).getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
    
}
