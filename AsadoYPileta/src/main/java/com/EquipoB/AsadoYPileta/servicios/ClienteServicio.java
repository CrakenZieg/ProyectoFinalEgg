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
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
import org.springframework.transaction.annotation.Transactional;
=======
import org.springframework.web.multipart.MultipartFile;
>>>>>>> desarrollo

@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;
<<<<<<< HEAD

    @Transactional
    public void crearCliente( List<Imagen> imagenes, String descripcion, List<Contacto> contactos) throws MiException {

        validar(imagenes, descripcion, contactos);

        Cliente cliente = new Cliente();

=======
    
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
>>>>>>> desarrollo
        cliente.setImagenes(imagenes);
        cliente.setDescripcion(descripcion);
        TipoContacto tipoContacto = new TipoContacto();
        tipoContacto.setTipo("telefono");
        Contacto contacto = new Contacto();
        contacto.setTipo(tipoContacto);
        contacto.setContacto(numeroCelular);
        ArrayList<Contacto> contactos = new ArrayList();
        
        cliente.setContactos(contactos);
<<<<<<< HEAD

=======
        cliente.setRol(Rol.CLIENTE);
        
>>>>>>> desarrollo
        clienteRepositorio.save(cliente);

    }

    public List<Cliente> listarCientes() {

        List<Cliente> clientes = new ArrayList();

        clientes = clienteRepositorio.findAll();

        return clientes;
    }
<<<<<<< HEAD

    @Transactional
    public void modificarCliente(String id, List<Imagen> imagenes,
            String descripcion, List<Contacto> contactos) throws MiException {
=======
    
    private void validar(String nombre, String apellido, MultipartFile[] imagenesInput, 
            String descripcion, String password,String password2) throws MiException {
>>>>>>> desarrollo

        validar(imagenes, descripcion, contactos);

        Optional<Cliente> respuesta = clienteRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Cliente cliente = respuesta.get();

            cliente.setImagenes(imagenes);
            cliente.setDescripcion(descripcion);
            cliente.setContactos(contactos);

            clienteRepositorio.save(cliente);

        }

    }

    @Transactional(readOnly = true)
    public Cliente getOne(String id) {

        return clienteRepositorio.getOne(id);
    }

    @Transactional
    public void eliminarCliente(String id) throws MiException {

        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            clienteRepositorio.deleteById(id);
        } else {
            throw new MiException("No se encontro el cliente");
        }

<<<<<<< HEAD
    }

    @Transactional
    public void bajaCliente(String id, String nombre, String apellido, List<Imagen> imagenes,
            String descripcion, List<Contacto> contactos) throws MiException {

        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Cliente cliente = new Cliente();
            cliente = respuesta.get();

            cliente.setAlta(false);
            clienteRepositorio.save(cliente);
        } else {
            throw new MiException("No se encontro el usuario");
        }
    }

    @Transactional
    public void recuperarCliente(String id, String nombre, String apellido, List<Imagen> imagenes,
            String descripcion, List<Contacto> contactos) throws MiException {
        
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        
        validar(imagenes, descripcion, contactos);

        Cliente cliente = new Cliente();
        cliente = respuesta.get();

        cliente.setAlta(true);

        clienteRepositorio.save(cliente);
    }

    private void validar(List<Imagen> imagenes,String descripcion, List<Contacto> contactos) throws MiException {

      

        if (imagenes.isEmpty() || imagenes == null) {
=======
        if (imagenesInput == null) {
>>>>>>> desarrollo

            throw new MiException("La imagen no puede ser nulo o estar vacio");
        }

        if (descripcion.isEmpty() || descripcion == null) {

            throw new MiException("La descripcion no puede ser nulo o estar vacia");
<<<<<<< HEAD
        }

        if (contactos.isEmpty() || contactos == null) {

            throw new MiException("El contacto no puede ser nulo o estar vacia");
=======
        }      
         
         if(password.isEmpty() || password==null ){
            throw new MiException ("el password no puede ser nulo, estar vacio o tener una longirud menos a 5 caracteres");
        }
        if(!password.equals(password2)){
             throw new MiException ("Los passwords deben ser iguales!");
>>>>>>> desarrollo
        }

    }

}
