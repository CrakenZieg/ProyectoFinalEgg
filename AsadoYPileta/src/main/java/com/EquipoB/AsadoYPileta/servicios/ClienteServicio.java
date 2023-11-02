package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Cliente;
import com.EquipoB.AsadoYPileta.entidades.Contacto;
import com.EquipoB.AsadoYPileta.entidades.Imagen;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.ClienteRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicio {
    
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    @Transactional
    public void crearCliente(String nombre, String apellido, List<Imagen> imagenes, 
            String descripcion, List<Contacto> contactos) throws MiException{
        
        validar(nombre, apellido, imagenes, descripcion, contactos);
        
        Cliente cliente = new Cliente();
        
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setImagenes(imagenes);
        cliente.setDescripcion(descripcion);
        cliente.setContactos(contactos);
        
        clienteRepositorio.save(cliente);
            
    }
    
    public List<Cliente> listarCientes(){
        
        List<Cliente> clientes = new ArrayList();
        
        clientes = clienteRepositorio.findAll();
        
        return clientes;
    }
    
    private void validar(String nombre, String apellido, List<Imagen> imagenes, 
            String descripcion, List<Contacto> contactos) throws MiException {

        if (nombre.isEmpty() || nombre == null) {

            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }

        if (apellido.isEmpty() || apellido == null) {

            throw new MiException("La contraseña no puede ser nulo o estar vacio");
        }

        if (imagenes.isEmpty() || imagenes == null) {

            throw new MiException("La imagen no puede ser nulo o estar vacio");
        }

        if (descripcion.isEmpty() || descripcion == null) {

            throw new MiException("La descripcion no puede ser nulo o estar vacia");
        }      
         
          if (contactos.isEmpty() || contactos == null) {

            throw new MiException("El contacto no puede ser nulo o estar vacia");
        }

    }
    
    
    

}
