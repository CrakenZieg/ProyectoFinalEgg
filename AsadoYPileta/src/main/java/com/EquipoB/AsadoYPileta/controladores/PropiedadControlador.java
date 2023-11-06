package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.Servicio;
import com.EquipoB.AsadoYPileta.entidades.Usuario;
import com.EquipoB.AsadoYPileta.enumeraciones.TipoPropiedad;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.excepciones.PermisosException;
import com.EquipoB.AsadoYPileta.servicios.PropiedadServicio;
import com.EquipoB.AsadoYPileta.servicios.PropietarioServicio;
import com.EquipoB.AsadoYPileta.servicios.ServicioServicio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/propiedad")
public class PropiedadControlador {
    
    @Autowired
    private PropiedadServicio propiedadServicio;
    
    @Autowired
    private PropietarioServicio propietarioServicio;

    @Autowired
    private ServicioServicio servicioServicio;

    private TipoPropiedad tipos;

    @GetMapping("/tipo/{tipo}")
    public String listar(@PathVariable String tipo, ModelMap model) {
        List<Propiedad> propiedades = new ArrayList<>();
        propiedades = propiedadServicio.listarPropiedadesPorTipo(tipo);
        model.addAttribute("propiedades", propiedades);
        model.addAttribute("tipos", tipos);
        return "index.html";
    }

    @GetMapping("/{id}")
    public String propiedad(@PathVariable String id, ModelMap model) {
        List<Servicio> servicios = new ArrayList<>();
        servicios = servicioServicio.listarServicios();
        model.addAttribute("propiedad", propiedadServicio.getOne(id));
        model.addAttribute("tipos", tipos);
        model.addAttribute("servicios", servicios);
        return "propiedad.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_PROPIETARIO','ROLE_ADMIN')")
    @GetMapping("/registrar")
    public String registrar(ModelMap model) {
        List<Servicio> servicios = new ArrayList<>();
        servicios = servicioServicio.listarServicios();
        model.addAttribute("tipos", tipos);
        model.addAttribute("servicios", servicios);
        return "registro_propiedad.html";
    }


    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_PROPIETARIO','ROLE_ADMIN')")
    @PostMapping("/registro")
    public String registro(@RequestParam String titulo, @RequestParam String descripcion,
            @RequestParam String ubicacion, @RequestParam String direccion,
            @RequestParam TipoPropiedad tipo, @RequestParam(required = false) String[] serviciosInput,
            @RequestParam MultipartFile[] imagenesInput, @RequestParam Double valor, HttpSession session) {        
        try {
            Usuario logueado = (Usuario) session.getAttribute("usuariosession");            
            propiedadServicio.crearPropiedad(titulo, descripcion, ubicacion,
                    direccion, tipo, serviciosInput, imagenesInput, valor, logueado);
        } catch (Exception ex) {
            System.out.println("Excepcion: " + ex);
        }
        return "index.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROPIETARIO')")
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap model, HttpSession session) throws PermisosException, MiException {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");        
        List<Servicio> servicios = new ArrayList<>();
        servicios = servicioServicio.listarServicios();
        Propiedad propiedad = propiedadServicio.getOne(id);
        if(!propietarioServicio.comprobarPropietario(logueado, propiedad)){
            throw new PermisosException("No es posible modificar la propiedad porque no te pertenece");
        }
        model.addAttribute("propiedad", propiedad);
        model.addAttribute("tipos", tipos);
        model.addAttribute("servicios", servicios);
        return "modificar_propiedad.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROPIETARIO')")
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, @RequestParam String titulo,
            @RequestParam String descripcion, @RequestParam String ubicacion,
            @RequestParam String direccion, @RequestParam TipoPropiedad tipo,
            @RequestParam(required = false) String[] serviciosInput,
            @RequestParam MultipartFile[] imagenesInput, @RequestParam Double valor,
            @RequestParam(required = false) String[] imagenesViejas, @RequestParam String estado) {
        try {
            propiedadServicio.modificarPropiedad(id, titulo, descripcion, ubicacion,
                    direccion, tipo, serviciosInput, imagenesInput, valor, imagenesViejas, estado);
        } catch (Exception ex) {
            System.out.println("Excepcion: " + ex);
        }
        return "index.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROPIETARIO')")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, HttpSession session) throws MiException, PermisosException {  
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        Propiedad propiedad = propiedadServicio.getOne(id);
        if(!propietarioServicio.comprobarPropietario(logueado, propiedad)){
            throw new PermisosException("No es posible eliminar la propiedad porque no te pertenece");
        }
        propiedadServicio.eliminar(id,logueado);
        return "index.html";
    }
}
