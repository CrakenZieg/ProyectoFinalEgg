
package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Propietario;
import com.EquipoB.AsadoYPileta.entidades.Usuario;
import com.EquipoB.AsadoYPileta.enumeraciones.Rol;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.PropietarioRepositorio;
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
    
    private Rol rol;
    
    public Propietario crearPropietario(Usuario usuario){
    
        if(usuario.getRol().equals(rol.ADMIN)){
            Optional<Propietario> respuesta = propietarioRepositorio.findById(usuario.getId());
            if(respuesta.isPresent()){
                propietario = respuesta.get();
            } else{
                propietario = new Propietario();
                propietario.setId(usuario.getId());
            }
        }
        if(logueado.getRol().equals(rol.CLIENTE)){
            usuarioServicio.cambiarRol(logueado.getId(), rol.PROPIETARIO);  
            propietario = propietarioServicio.getOne(logueado.getId());
        }
    }
    
    
    
    public List<Propietario> listarPropietario() {
        List<Propietario> clientes = new ArrayList();
        clientes = propietarioRepositorio.findAll();
        return clientes;
    }
    
    @Transactional(readOnly = true)
    public Propietario getOne(String id) throws MiException {
        Optional<Propietario> respuesta = propietarioRepositorio.findById(id);
        if(respuesta.isPresent()){
            return respuesta.get();
        } else {
            throw new MiException("No se encontro el propietario");
        }
    }

    @Transactional
    public void eliminarCliente(String id) throws MiException {
        Optional<Propietario> respuesta = propietarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            propietarioRepositorio.deleteById(id);
        } else {
            throw new MiException("No se encontro el propietario");
        }
    }
    
}
