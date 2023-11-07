package com.EquipoB.AsadoYPileta.servicios;


import com.EquipoB.AsadoYPileta.entidades.Cliente;
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

    private Rol rol;

    public Propietario crearPropietario(Usuario usuario) {
        Propietario propietario = null;
        Cliente cliente = null;
        if (usuario.getRol().equals(rol.ADMIN)) {
            Optional<Propietario> respuestaProp = propietarioRepositorio.findById(usuario.getId());
            if (respuestaProp.isPresent()) {
                propietario = respuestaProp.get();
                return propietario;
            } else {
                Optional<Cliente> respuestaCli = clienteRepositorio.findById(usuario.getId());
                if (respuestaCli.isPresent()) {
                    cliente = respuestaCli.get();
                } else {
                    cliente = new Cliente();
                    cliente.setId(usuario.getId());
                    cliente.setUsuario(usuario);
                }
            }
            propietario = new Propietario();
            propietario.setId(usuario.getId());
            propietario.setCliente(cliente);
            return propietario;
        } else {
            Optional<Cliente> respuestaCli = clienteRepositorio.findById(usuario.getId());
            if (respuestaCli.isPresent()) {
                cliente = respuestaCli.get();
            } else {
                cliente = new Cliente();
                cliente.setId(usuario.getId());
                cliente.setUsuario(usuario);
            }
        }
        propietario = new Propietario();
        propietario.setId(usuario.getId());
        propietario.setCliente(cliente);
        usuario.setRol(rol.PROPIETARIO);
        usuarioRepositorio.save(usuario);
        return propietario;
    }

    public List<Propietario> listarPropietario() {
        List<Propietario> clientes = new ArrayList();
        clientes = propietarioRepositorio.findAll();
        return clientes;
    }

    @Transactional(readOnly = true)
    public Propietario getOne(String id) throws MiException {
        Optional<Propietario> respuesta = propietarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new MiException("No se encontro el propietario");
        }
    }
    
    public boolean comprobarPropietario(Usuario logueado, Propiedad propiedad) throws MiException{
        return getOne(logueado.getId()).getPropiedades().contains(propiedad);
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