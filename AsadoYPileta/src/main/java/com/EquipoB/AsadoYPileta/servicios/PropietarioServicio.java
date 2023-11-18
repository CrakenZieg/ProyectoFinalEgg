package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Cliente;
import com.EquipoB.AsadoYPileta.entidades.Contacto;
import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.Propietario;
import com.EquipoB.AsadoYPileta.entidades.Usuario;
import com.EquipoB.AsadoYPileta.enumeraciones.Rol;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.ClienteRepositorio;
import com.EquipoB.AsadoYPileta.repositorios.PropietarioRepositorio;
import com.EquipoB.AsadoYPileta.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PropietarioServicio {

    @Autowired
    private PropietarioRepositorio propietarioRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    public Propietario crearPropietario(Usuario usuario) {
        Propietario propietario = null;
        Optional<Cliente> respuesta = clienteRepositorio.findById(usuario.getId());
        Cliente cliente = respuesta.get();
        propietario = new Propietario();
        propietario.setId(usuario.getId());
        propietario.setCliente(cliente);
        propietarioRepositorio.save(propietario);
        usuario.setRol(Rol.PROPIETARIO);
        usuarioRepositorio.save(usuario);
        return propietario;
    }

    public List<Propietario> listarPropietario() {
        List<Propietario> clientes = new ArrayList();
        clientes = propietarioRepositorio.findAll();
        return clientes;
    }

    @Transactional(readOnly = true)
    public Optional<Propietario> getOne(String id) throws MiException {
        return propietarioRepositorio.findById(id);
    }
  
     public List<Contacto> mostrarContactos (String idUsuario){
        Propietario propietario = propietarioRepositorio.getOne(idUsuario);
         
        List<Contacto> contactosUsuario = propietario.getCliente().getContactos();
        return contactosUsuario;
    }
    
    public boolean comprobarPropietario(Usuario logueado, Propiedad propiedad) throws MiException{ // el método comprueba si un usuario logueado es propietario de una propiedad específica y devuelve un valor booleano
        Optional<Propietario> respuesta = propietarioRepositorio.findById(logueado.getId());
        if (respuesta.isPresent()) {
            return respuesta.get().getPropiedades().contains(propiedad);
        } else {
            throw new MiException("No se encontro el propietario");
        }
    }

    @Transactional
    public void eliminarPropietario(String id) throws MiException {
        Optional<Propietario> respuesta = propietarioRepositorio.findById(id);
        Propietario propietario = null;
        if (respuesta.isPresent()) {
            propietario = respuesta.get();
            if (propietario.getPropiedades().size() != 0) {
                throw new MiException("No se puede eliminar el propietario si tiene");
            } else {
                propietarioRepositorio.deleteById(id);
            }
        } else {
            throw new MiException("No se encontro el propietario");
        }
    }

}