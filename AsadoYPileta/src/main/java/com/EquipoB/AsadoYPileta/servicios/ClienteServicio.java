package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Cliente;
import com.EquipoB.AsadoYPileta.entidades.Contacto;
import com.EquipoB.AsadoYPileta.entidades.Imagen;
import com.EquipoB.AsadoYPileta.entidades.TipoContacto;
import com.EquipoB.AsadoYPileta.entidades.Usuario;
import com.EquipoB.AsadoYPileta.enumeraciones.Rol;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.ClienteRepositorio;
import com.EquipoB.AsadoYPileta.repositorios.ContactoRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;
        
    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private TipoContactoServicio tipoContactoServicio;
    
    @Autowired
    private ContactoRepositorio contactoRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    
    private Rol rol;

    @Transactional
    public void crearCliente(String email, String nombre, String apellido, String descripcion,
            String password, String password2, MultipartFile[] imagenesInput,
            String[] tipoContactoInput, String[] contactosInput) throws MiException, Exception {

        validar(email, nombre, apellido, descripcion, password, password2, imagenesInput,
                tipoContactoInput, contactosInput);
        
        usuarioServicio.crearUsuario(email, password, rol.CLIENTE);
        Usuario usuario = usuarioServicio.getPorEmail(email);
        
        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario);
        cliente.setId(usuario.getId());

        List<Imagen> imagenes = new ArrayList<>();
        imagenes = imagenServicio.guardarVarias(imagenesInput);

        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setImagenes(imagenes);
        cliente.setDescripcion(descripcion);
        ArrayList<Contacto> contactos = new ArrayList();
        for (int i = 0; i < tipoContactoInput.length; i++) {
            TipoContacto tipo = tipoContactoServicio.getOnePorTipo(tipoContactoInput[i]);
            Contacto contacto = new Contacto();
            contacto.setTipo(tipo);
            contacto.setContacto(contactosInput[i]);
            contactoRepositorio.save(contacto);
            contactos.add(contacto);
        }
        cliente.setContactos(contactos);
        clienteRepositorio.save(cliente);
    }

    public List<Cliente> listarCientes() {
        List<Cliente> clientes = new ArrayList();
        clientes = clienteRepositorio.findAll();
        return clientes;
    }
  
    @Transactional
    private void modificarCliente(String email, String id, String nombre, String apellido, 
            String descripcion, String password, String password2, MultipartFile[] imagenesInput,
            String[] tipoContactoInput, String[] contactosInput, String[] imagenesViejas) throws MiException, Exception {

        validar(email, nombre, apellido, descripcion, password, password2, imagenesInput,
                tipoContactoInput, contactosInput);

        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            
            Cliente cliente = respuesta.get();            
            List<Imagen> imagenes = cliente.getImagenes();
            
            if(imagenesViejas != null){ 
                if(imagenesViejas.length != 0){
                    imagenes = imagenServicio.filtrar(imagenes, 
                            imagenesViejas);
                }
            }     
            if(imagenesInput != null){
                if(imagenesInput.length != 0){
                    imagenes.addAll(imagenServicio.guardarVarias(imagenesInput));
                } 
            }
            cliente.getUsuario().setEmail(email);
            cliente.getUsuario().setPassword(new BCryptPasswordEncoder().encode(password));
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setImagenes(imagenes);
            cliente.setDescripcion(descripcion);
            ArrayList<Contacto> contactos = new ArrayList();
            for (int i = 0; i < tipoContactoInput.length; i++) {
                TipoContacto tipo = tipoContactoServicio.getOnePorTipo(tipoContactoInput[i]);
                Contacto contacto = new Contacto();
                contacto.setTipo(tipo);
                contacto.setContacto(contactosInput[i]);
                contactos.add(contacto);
            }
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
    }

    private void validar(String email, String nombre, String apellido, String descripcion,
            String password, String password2, MultipartFile[] imagenesInput,
            String[] tipoContactoInput, String[] contactosInput) throws MiException {
        if (email == null || email.trim().isEmpty()) {
            throw new MiException("El email no puede ser nulo o estar vacio");
        }        
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new MiException("El apellido no puede ser nulo o estar vacio");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new MiException("La descripcion no puede ser nulo o estar vacio");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new MiException("La descripcion no puede ser nulo o estar vacio");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new MiException("El password no puede ser nulo o estar vacio");
        }
        if (!password.equals(password2)) {
            throw new MiException("El password tiene que ser igual en ambos campos");
        }
        if (imagenesInput == null || imagenesInput.length == 0) {
            throw new MiException("Debes ingresar por lo menos una imagen");
        }
        if (tipoContactoInput == null || tipoContactoInput.length == 0) {
            throw new MiException("Debes ingresar por lo menos un contacto");
        }
        if (contactosInput == null || contactosInput.length == 0 || contactosInput[0].trim().isEmpty()) {
            throw new MiException("Debes ingresar por lo menos un contacto");
        }
    }

}
