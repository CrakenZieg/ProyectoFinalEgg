package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Cliente;
import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.Propietario;
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
import com.EquipoB.AsadoYPileta.repositorios.ClienteRepositorio;
import com.EquipoB.AsadoYPileta.repositorios.PropietarioRepositorio;
import com.EquipoB.AsadoYPileta.repositorios.UsuarioRepositorio;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private PropietarioRepositorio propietarioRepositorio;
    
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
    public void crearUsuario(String email, String password, Rol rol) throws MiException, Exception {
        
        validar(email, password, rol);
        
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
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
    public void modificarUsuario(String id, String email, String password, Rol rol,
            Date fechaAlta, Boolean alta) throws MiException {
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
        } else {
            throw new MiException("No se encontro el usuario");
        }
    }
    
    @Transactional(readOnly = true)
    public Usuario getOne(String id) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return usuarioRepositorio.getOne(id);
        } else {
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public Usuario getPorEmail(String email) {
        Usuario respuesta = usuarioRepositorio.buscarPorEmail(email);
        return respuesta;
    }
    
    @Transactional
    public Usuario cambiarRol(String id, Rol rol) throws MiException {
        Optional<Usuario> respuestaUsuario = usuarioRepositorio.findById(id);
        Usuario usuario = null;
        if (respuestaUsuario.isPresent()) {
            usuario = respuestaUsuario.get();
            switch (rol) {
                case ADMIN: {
                    usuario.setRol(rol.ADMIN);
                    Optional<Cliente> respuestaCli = clienteRepositorio.findById(id);
                    if (!respuestaCli.isPresent()) {
                        Cliente cliente = new Cliente();
                        cliente.setUsuario(usuario);
                        clienteRepositorio.save(cliente);
                        Optional<Propietario> respuestaProp = propietarioRepositorio.findById(id);
                        if (!respuestaProp.isPresent()) {
                            Propietario propietario = new Propietario();
                            propietario.setCliente(cliente);
                            propietarioRepositorio.save(propietario);
                        }
                    }
                    usuarioRepositorio.save(usuario);
                    break;
                }
                case CLIENTE: {
                    if (usuario.getRol().equals(rol.CLIENTE)) {
                        throw new MiException("Su rol ya es de Cliente!");
                    }
                    if (usuario.getRol().equals(rol.PROPIETARIO) || usuario.getRol().equals(rol.ADMIN)) {
                        Propietario propietario = propietarioRepositorio.getById(id);
                        if (propietario.getPropiedades().size() != 0) {
                            throw new MiException("No es posible modificar el rol del cliente si este tiene propiedades");
                        }
                        propietarioRepositorio.delete(propietario);
                        usuario.setRol(rol.CLIENTE);
                        usuarioRepositorio.save(usuario);
                        break;
                    }                    
                }
                case PROPIETARIO: {
                    if (usuario.getRol().equals(rol.PROPIETARIO)) {
                        throw new MiException("Su rol ya es de Propietario!");
                    } else {
                        Optional<Propietario> respuestaProp = propietarioRepositorio.findById(id);
                        if (respuestaProp.isPresent()) {
                            usuario.setRol(rol.PROPIETARIO);
                            usuarioRepositorio.save(usuario);
                        } else {
                            Propietario propietario = new Propietario();
                            Optional<Cliente> respuestaCliente = clienteRepositorio.findById(id);
                            if (respuestaCliente.isPresent()) {
                                propietario.setCliente(respuestaCliente.get());
                            } else {
                                Cliente cliente = new Cliente();
                                propietario.setCliente(cliente);
                            }
                            propietarioRepositorio.save(propietario);
                        }
                    }
                    break;
                }
            }
        }
        return usuario;
    }
    
    @Transactional
    public void bajaUsuario(String id) throws MiException {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = new Usuario();
            usuario = respuesta.get();
            usuario.setAlta(false);
            String rol = usuario.getRol().toString();
            if (rol.equals("PROPIETARIO")) {
                Propietario propietario = propietarioRepositorio.getById(usuario.getId());
                List<Propiedad> propiedades = propietario.getPropiedades();
                for (Propiedad propiedad : propiedades) {
                    propiedad.setEstado(Boolean.FALSE);
                } 
            }
            usuarioRepositorio.save(usuario);
        } else {
            throw new MiException("No se encontro el usuario");
        }
    }
    
    @Transactional
    public void recuperarUsuario(String id) throws MiException {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = new Usuario();
            usuario = respuesta.get();
            usuario.setAlta(true);
            usuarioRepositorio.save(usuario);
        } else {
            throw new MiException("No se encontro el usuario");
        }
    }
    
    @Transactional
    public void eliminarUsuario(String id) throws MiException {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = new Usuario();
            usuario = respuesta.get();
            Rol rol = usuario.getRol();
            switch (rol) {
                case PROPIETARIO: {
                    Propietario propietario = propietarioRepositorio.getById(usuario.getId());
                    if (propietario.getPropiedades().size() == 0) {
                        propietarioRepositorio.delete(propietario);
                    } else {
                        throw new MiException("No es posible eliminar el propietario si este tiene propiedades");
                    }
                    Cliente cliente = clienteRepositorio.getById(usuario.getId());
                    /* Metodo que controle que no haya reservas activas */
                    if (true) {
                        clienteRepositorio.delete(cliente);
                    }
                    break;
                }
                case CLIENTE: {
                    Cliente cliente = clienteRepositorio.getById(usuario.getId());
                    /* Metodo que controle que no haya reservas activas */
                    if (true) {
                        clienteRepositorio.delete(cliente);
                    }
                    break;
                }
                case ADMIN: {
                    /* Metodo que controle que este habilitada la eliminacion */
                }
            }
            usuarioRepositorio.delete(usuario);
        }
    }
    
    private void validar(String email, String password, Rol rol) throws MiException {
        if (email == null || email.trim().isEmpty()) {
            throw new MiException("El Email no puede ser nulo o estar vacio");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new MiException("La contraseña no puede ser nulo o estar vacio");
        }
        if (rol == null) {
            throw new MiException("El Rol no puede ser nulo o estar vacio");
        }
    }
}
