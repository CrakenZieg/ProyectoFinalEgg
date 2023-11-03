package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Cliente;
import com.EquipoB.AsadoYPileta.entidades.Contacto;
import com.EquipoB.AsadoYPileta.entidades.Imagen;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.ClienteRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Transactional
    public void crearCliente( List<Imagen> imagenes, String descripcion, List<Contacto> contactos) throws MiException {

        validar(imagenes, descripcion, contactos);

        Cliente cliente = new Cliente();

        cliente.setImagenes(imagenes);
        cliente.setDescripcion(descripcion);
        cliente.setContactos(contactos);

        clienteRepositorio.save(cliente);

    }

    public List<Cliente> listarCientes() {

        List<Cliente> clientes = new ArrayList();

        clientes = clienteRepositorio.findAll();

        return clientes;
    }

    @Transactional
    public void modificarCliente(String id, List<Imagen> imagenes,
            String descripcion, List<Contacto> contactos) throws MiException {

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
