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
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+usuario.getRol().toString());
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

    
    public void crearUsuario(String email, String password, Rol rol) throws MiException, Exception {


        validar( email, password, rol);

        Usuario usuario = new Usuario();

        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setRol(rol);
        usuario.setAlta(true);

        usuarioRepositorio.save(usuario);

    }
     @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {

        List<Usuario> usuarios = new ArrayList();

        usuarios = usuarioRepositorio.findAll();

        return usuarios;
    }

    @Transactional
    public void modificarUsuario(String id, String nombre, String apellido, String email, String password, Rol rol, Date fechaAlta,
            Boolean alta) throws MiException {

        validar(email, password, rol);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();

            usuario.setEmail(email);
            usuario.setPassword(password);
            usuario.setRol(rol);
            usuario.setFechaAlta(fechaAlta);
            usuario.setAlta(alta);

            usuarioRepositorio.save(usuario);
        }
    }
    @Transactional(readOnly = true)
    public Usuario getOne(String id){
        
        return usuarioRepositorio.getOne(id);
    }
    
    
    @Transactional
    public void eliminarUsuario(String id) throws MiException{
    
        Optional<Usuario> respuesta= usuarioRepositorio.findById(id);
        if(respuesta.isPresent()){
             usuarioRepositorio.deleteById(id);
        }else{
            throw new MiException ("No se encontro el usuario");
        }
       
    }
    
    @Transactional
    public void cambiarRol(String id){
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
    	
    	if(respuesta.isPresent()) {
    		
    		Usuario usuario = respuesta.get();
    		
    		if(usuario.getRol().equals(Rol.PROPIETARIO)) {
    			
    		usuario.setRol(Rol.CLIENTE);
    		
    		}else if(usuario.getRol().equals(Rol.CLIENTE)) {
    			usuario.setRol(Rol.PROPIETARIO);
    		}
    	}
    }

    @Transactional
    public void bajaUsuario(String id, String email, String password, Rol rol, 
            Date fechaAlta, Boolean activo) throws MiException{

        Optional<Usuario> respuesta= usuarioRepositorio.findById(id);
         if(respuesta.isPresent()){
            Usuario usuario = new Usuario();
            usuario = respuesta.get();
           
            usuario.setAlta(false);
             usuarioRepositorio.save(usuario);
        }else{
            throw new MiException ("No se encontro el usuario");
        }
    }
    
    @Transactional
    public void recuperarUsuario(String id, String nombre, String apellido, String email, String password, Rol rol, Date fechaAlta,
            Boolean alta) throws MiException{
        Optional<Usuario> respuesta= usuarioRepositorio.findById(id);
         validar( email, password, rol);
       
            Usuario usuario = new Usuario();
            usuario = respuesta.get();
            
            usuario.setAlta(true);
          
            usuarioRepositorio.save(usuario);
    }

    private void validar(String email, String password, Rol rol) throws MiException {

      
        
        if (email.isEmpty() || email == null) {

            throw new MiException("El Email no puede ser nulo o estar vacio");
        }

        if (password.isEmpty() || password == null) {

            throw new MiException("La contraseña no puede ser nulo o estar vacio");
        }

        if ( rol == null) {

            throw new MiException("El Rol no puede ser nulo o estar vacio");
        }

       
    }

}
