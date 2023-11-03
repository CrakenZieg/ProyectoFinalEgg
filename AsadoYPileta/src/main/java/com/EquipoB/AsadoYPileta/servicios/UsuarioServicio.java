package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Usuario;
import com.EquipoB.AsadoYPileta.enumeraciones.Rol;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.UsuarioRepositorio;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service

public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            return null;
        }
    }

    @Transactional
    public void crearUsuario(String nombre, String apellido, String email, String password, String password2, Rol rol) throws MiException, Exception {
        validar(nombre, apellido, email, password, password2, rol, Boolean.TRUE);

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setRol(Rol.CLIENTE);
        usuario.setAlta(true);
        usuarioRepositorio.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        return usuarios;
    }

    @Transactional
    public void modificarUsuario(String nombre, String apellido, String id, String email, String password, Rol rol, Date fechaAlta, Boolean activo) throws MiException {
        validar(nombre, apellido, email, password, password, rol, activo);
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setEmail(email);
            usuario.setPassword(password);
            usuario.setRol(rol);
            usuario.setFechaAlta(fechaAlta);
            usuario.setAlta(activo);
            usuarioRepositorio.save(usuario);
        }
    }

    @Transactional(readOnly = true)
    public Usuario getOne(String id) {
        return usuarioRepositorio.getOne(id);
    }

    @Transactional
    public void eliminarUsuario(String id) throws MiException {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            usuarioRepositorio.deleteById(id);
        } else {
            throw new MiException("No se encontro el usuario");
        }

    }

    @Transactional
    public void cambiarRol(String id) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();

            if (usuario.getRol().equals(Rol.PROPIETARIO)) {

                usuario.setRol(Rol.CLIENTE);

            } else if (usuario.getRol().equals(Rol.CLIENTE)) {
                usuario.setRol(Rol.PROPIETARIO);
            }
        }

    }

    @Transactional
    public void eliminarUsuarioG(String id) throws MiException {
    }

    @Transactional
    public void bajaUsuario(String id, String email, String password, Rol rol,
            Date fechaAlta, Boolean activo) throws MiException {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setAlta(false);
            usuarioRepositorio.save(usuario);
        } else {
            throw new MiException("No se encontro el usuario");
        }
    }

    @Transactional
    public void recuperarUsuario(String nombre, String apellido, String id, String email, String password,
            Rol rol, Date fechaAlta, Boolean activo) throws MiException {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        validar(nombre, apellido, email, password, password, rol, activo);
        Usuario usuario = new Usuario();
        usuario = respuesta.get();
        usuario.setAlta(true);
        usuarioRepositorio.save(usuario);
    }

    private void validar(String nombre, String apellido, String email, String password, String password2, Rol rol, Boolean activo) throws MiException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacío");
        }
        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("El Email no puede ser nulo o estar vacio");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
        if (rol == null) {
            throw new MiException("El Rol no puede ser nulo o estar vacio");
        }
        if (activo == false) {
            throw new MiException("El usuario tiene que estar activo");
        }

    }

}
