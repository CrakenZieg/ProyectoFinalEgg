package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Usuario;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void crearUsuario(String email, String password, Rol rol, Date fechaAlta, Boolean activo) throws MiException {

        validar(email, password, rol, fechaAlta, activo);

        Usuario usuario = new Usuario();

        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setRol(rol);
        usuario.setFechaAlta(fechaAlta);
        usuario.setActivo(activo);

        usuarioRepositorio.save(usuario);

    }

    public List<Usuario> listarUsuarios() {

        List<Usuario> usuarios = new ArrayList();

        usuarios = usuarioRepositorio.findAll();

        return usuarios;
    }

    @Transactional
    public void modificarUsuario(String id, String email, String password, Rol rol, Date fechaAlta, Boolean activo) throws MiException {

        validar(email, password, rol, fechaAlta, activo);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();

            usuario.setEmail(email);
            usuario.setPassword(password);
            usuario.setRol(rol);
            usuario.setFechaAlta(fechaAlta);
            usuario.setActivo(activo);

            usuarioRepositorio.save(usuario);
        }
    }
    
    public Usuario getOne(String id){
        
        return usuarioRepositorio.getOne(id);
    }
    
    
     @Transactional
    public void eliminarUsuario(String id, String email, String password, Rol rol, Date fechaAlta, Boolean activo) throws MiException{
    
        validar(email, password, rol, fechaAlta, activo);
        
        usuarioRepositorio.deleteById(id);
    
    }

    private void validar(String email, String password, Rol rol, Date fechaAlta, Boolean activo) throws MiException {

        if (email.isEmpty() || email == null) {

            throw new MiException("El Email no puede ser nulo o estar vacio");
        }

        if (password.isEmpty() || password == null) {

            throw new MiException("La contraseña no puede ser nulo o estar vacio");
        }

        if (rol.isEmpty() || rol == null) {

            throw new MiException("El Rol no puede ser nulo o estar vacio");
        }

        if (fechaAlta == null) {

            throw new MiException("La fecha de alta no puede ser nulo ");
        }

        if (activo == false) {

            throw new MiException("El usuario tiene que estar activo");
        }

    }
}
