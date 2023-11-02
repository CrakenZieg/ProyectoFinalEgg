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
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public void crearUsuario(String email, String password, Rol rol, Date fechaAlta, Boolean activo) throws MiException {

        validar(email, password, rol, fechaAlta, activo);

        Usuario usuario = new Usuario();

        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setRol(rol);
        usuario.setFechaAlta(fechaAlta);
        usuario.setAlta(activo);

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
            usuario.setAlta(activo);

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
      public void bajaUsuario(String id, String email, String password, Rol rol, Date fechaAlta, Boolean activo) throws MiException{
        Optional<Usuario> respuesta= usuarioRepositorio.findById(id);
        
       validar(email, password, rol, fechaAlta, activo);
            Usuario usuario = new Usuario();
            usuario = respuesta.get();
           
            usuario.setAlta(false);
          
            usuarioRepositorio.save(usuario);
    }
    public void recuperarUsuario(String id, String email, String password, Rol rol, Date fechaAlta, Boolean activo) throws MiException{
        Optional<Usuario> respuesta= usuarioRepositorio.findById(id);
         validar(email, password, rol, fechaAlta, activo);
       
            Usuario usuario = new Usuario();
            usuario = respuesta.get();
            
            usuario.setAlta(true);
          
            usuarioRepositorio.save(usuario);
    }

    private void validar(String email, String password, Rol rol, Date fechaAlta, Boolean activo) throws MiException {

        if (email.isEmpty() || email == null) {

            throw new MiException("El Email no puede ser nulo o estar vacio");
        }

        if (password.isEmpty() || password == null) {

            throw new MiException("La contraseña no puede ser nulo o estar vacio");
        }

        if ( rol == null) {

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
