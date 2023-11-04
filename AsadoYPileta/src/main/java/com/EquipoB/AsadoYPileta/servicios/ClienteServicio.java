package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Cliente;
import com.EquipoB.AsadoYPileta.entidades.Contacto;
import com.EquipoB.AsadoYPileta.entidades.Imagen;
import com.EquipoB.AsadoYPileta.entidades.TipoContacto;
import com.EquipoB.AsadoYPileta.enumeraciones.Rol;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.ClienteRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClienteServicio {
    
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    @Autowired
    private ImagenServicio imagenServicio;
    
    @Transactional
    public void crearCliente(String nombre, String apellido,String password,String password2, MultipartFile[] imagenesInput, 
            String descripcion, String numeroCelular) throws MiException, Exception{
        
        validar(nombre, apellido, imagenesInput, descripcion,password,password2);
        
        Cliente cliente = new Cliente();
        
        List<Imagen> imagenes = new ArrayList<>();
        imagenes = imagenServicio.guardarVarias(imagenesInput);
        
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setPassword(new BCryptPasswordEncoder().encode(password));
        cliente.setImagenes(imagenes);
        cliente.setDescripcion(descripcion);
        TipoContacto tipoContacto = new TipoContacto();
        tipoContacto.setTipo("telefono");
        Contacto contacto = new Contacto();
        contacto.setTipo(tipoContacto);
        contacto.setContacto(numeroCelular);
        ArrayList<Contacto> contactos = new ArrayList();
        
        cliente.setContactos(contactos);
        cliente.setRol(Rol.CLIENTE);
        
        clienteRepositorio.save(cliente);
            
    }
    
    public List<Cliente> listarCientes(){
        
        List<Cliente> clientes = new ArrayList();
        
        clientes = clienteRepositorio.findAll();
        
        return clientes;
    }
    
    private void validar(String nombre, String apellido, MultipartFile[] imagenesInput, 
            String descripcion, String password,String password2) throws MiException {

        if (nombre == null || nombre.trim().isEmpty() ) {

            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }

        if (apellido == null || apellido.trim().isEmpty() ) {

            throw new MiException("La contraseña no puede ser nulo o estar vacio");
        }

        if (imagenesInput == null) {

            throw new MiException("La imagen no puede ser nulo o estar vacio");
        }

        if ( descripcion == null || descripcion.trim().isEmpty()  ) {

            throw new MiException("La descripcion no puede ser nulo o estar vacia");
        }      
         
         if(password==null || password.trim().isEmpty()){
            throw new MiException ("el password no puede ser nulo, estar vacio o tener una longirud menos a 5 caracteres");
        }
        if(!password.equals(password2)){
             throw new MiException ("Los passwords deben ser iguales!");
        }

    }
    
    
    

}
