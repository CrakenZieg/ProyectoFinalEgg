package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.entidades.Comentario;
import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.Reserva;
import com.EquipoB.AsadoYPileta.entidades.Servicio;
import com.EquipoB.AsadoYPileta.entidades.TipoPropiedad;
import com.EquipoB.AsadoYPileta.entidades.Usuario;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.excepciones.PermisosException;
import com.EquipoB.AsadoYPileta.servicios.ComentarioServicio;
import com.EquipoB.AsadoYPileta.servicios.FiltroDisponibilidadServicio;
import com.EquipoB.AsadoYPileta.servicios.PropiedadServicio;
import com.EquipoB.AsadoYPileta.servicios.PropietarioServicio;
import com.EquipoB.AsadoYPileta.servicios.ReservaServicio;
import com.EquipoB.AsadoYPileta.servicios.ServicioServicio;
import com.EquipoB.AsadoYPileta.servicios.TipoPropiedadServicio;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/propiedad")
public class PropiedadControlador {

    @Autowired
    private PropiedadServicio propiedadServicio;

    @Autowired
    private PropietarioServicio propietarioServicio;

    @Autowired
    private ServicioServicio servicioServicio;

    @Autowired
    private ComentarioServicio comentarioServicio;

    @Autowired
    private TipoPropiedadServicio tipoPropiedadServicio;

    @Autowired
    private FiltroDisponibilidadServicio filtroDisponibilidadServicio;

    @Autowired
    private ReservaServicio reservaServicio;


    @GetMapping("/tipo/{tipo}")
    public ModelAndView listar(@PathVariable String tipo, ModelMap modelo) throws MiException {
        List<Propiedad> propiedades = new ArrayList<>();
        propiedades = propiedadServicio.listarPropiedadesPorTipo(tipo);
        List<TipoPropiedad> tipoPropiedades = new ArrayList<>();
        tipoPropiedades = tipoPropiedadServicio.listarTipoPropiedad();
        modelo.addAttribute("propiedades", propiedades);
        modelo.addAttribute("tipoPropiedades", tipoPropiedades);
        return new ModelAndView("index.html", modelo);
    }

    @GetMapping("/{id}")
    public ModelAndView propiedad(@PathVariable String id, ModelMap modelo) {
        Propiedad propiedad = propiedadServicio.getOne(id);
        List<Servicio> servicios = new ArrayList<>();
        servicios = servicioServicio.listarServicios();
        List<Comentario> comentarios = new ArrayList<>();
        comentarios = comentarioServicio.findComentariosByPropiedadId(id);
        List<TipoPropiedad> tipoPropiedades = new ArrayList<>();
        tipoPropiedades = tipoPropiedadServicio.listarTipoPropiedad();
        List<Reserva> reservas = reservaServicio.reservasFuturas(id);
        List<String> fechasReservadas = reservaServicio.diasReservados(reservas);
        List<String> fechasDisponibles = filtroDisponibilidadServicio.obtenerDiasHabilitados(propiedad.getFiltroDisponibilidad());
        modelo.addAttribute("fechasReservadas", fechasReservadas);
        modelo.addAttribute("fechasDisponibles", fechasDisponibles);
        modelo.addAttribute("propiedad", propiedad);
        modelo.addAttribute("tipoPropiedades", tipoPropiedades);
        modelo.addAttribute("servicios", servicios);
        modelo.addAttribute("comentarios", comentarios);
        return new ModelAndView("propiedad.html", modelo);

    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_PROPIETARIO')")
    @GetMapping("/registrar")
    public ModelAndView registrar(ModelMap modelo) {
        List<Servicio> servicios = new ArrayList<>();
        servicios = servicioServicio.listarServicios();
        List<TipoPropiedad> tipoPropiedades = new ArrayList<>();
        tipoPropiedades = tipoPropiedadServicio.listarTipoPropiedad();
        modelo.addAttribute("tipoPropiedades", tipoPropiedades);
        modelo.addAttribute("servicios", servicios);
        return new ModelAndView("registro_propiedad.html", modelo);
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_PROPIETARIO')")
    @PostMapping("/registro")
    public ModelAndView registro(@RequestParam String titulo, @RequestParam String descripcion,
            @RequestParam String tipo, @RequestParam(required = false) String[] serviciosInput,
            @RequestParam MultipartFile[] imagenesInput, @RequestParam Double valor, HttpSession session,
            @RequestParam String pais, @RequestParam String provincia, @RequestParam String departamento, @RequestParam String localidad,
            @RequestParam String calle, @RequestParam String numeracion, @RequestParam String observaciones,
            @RequestParam Double latitud, @RequestParam Double longitud, @RequestParam(required = false) String fechaInicioReserva,
            @RequestParam(required = false) String fechaFinReserva, @RequestParam(required = false) String[] mensualReserva,
            @RequestParam(required = false) String[] diarioReserva, @RequestParam(required = false) String[] porFechaReserva) throws Exception {

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        propiedadServicio.crearPropiedad(titulo, descripcion, tipo, serviciosInput, imagenesInput, valor, logueado, pais, provincia,
                departamento, localidad, calle, numeracion, observaciones, latitud, longitud, fechaInicioReserva,
                fechaFinReserva, mensualReserva, diarioReserva, porFechaReserva);
        return new ModelAndView("redirect:/");
    }

    @PreAuthorize("hasRole('ROLE_PROPIETARIO')")
    @GetMapping("/modificar/{id}")
    public ModelAndView modificar(@PathVariable String id, ModelMap modelo, HttpSession session) throws PermisosException, MiException {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        List<Servicio> servicios = new ArrayList<>();
        servicios = servicioServicio.listarServicios();
        List<TipoPropiedad> tipoPropiedades = new ArrayList<>();
        tipoPropiedades = tipoPropiedadServicio.listarTipoPropiedad();
        Propiedad propiedad = propiedadServicio.getOne(id);
        if (!propietarioServicio.comprobarPropietario(logueado, propiedad)) {
            throw new PermisosException("No es posible modificar la propiedad porque no te pertenece");
        }
        List<Integer> fechasDisponibles = filtroDisponibilidadServicio.listaDeEnterosDiasReservados(propiedad);
        List<Integer> mesesDisponibles = propiedad.getFiltroDisponibilidad() != null
                ? Arrays.stream(propiedad.getFiltroDisponibilidad().getMensual()).boxed().collect(Collectors.toList())
                : null;
        modelo.addAttribute("mesesDisponibles", mesesDisponibles);
        modelo.addAttribute("fechasDisponibles", fechasDisponibles);
        modelo.addAttribute("propiedad", propiedad);
        modelo.addAttribute("tipoPropiedades", tipoPropiedades);
        modelo.addAttribute("servicios", servicios);
        return new ModelAndView("modificar_propiedad.html", modelo);
    }

    @PreAuthorize("hasRole('ROLE_PROPIETARIO')")
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, @RequestParam String titulo,
            @RequestParam String descripcion, @RequestParam String tipo,
            @RequestParam(required = false) String[] serviciosInput,
            @RequestParam MultipartFile[] imagenesInput, @RequestParam Double valor,
            @RequestParam(required = false) String[] imagenesViejas, @RequestParam String estado,
            @RequestParam String pais, @RequestParam String provincia, @RequestParam String departamento, @RequestParam String localidad,
            @RequestParam String calle, @RequestParam String numeracion, @RequestParam String observaciones,
            @RequestParam Double latitud, @RequestParam Double longitud, @RequestParam(required = false) String fechaInicioReserva,
            @RequestParam(required = false) String fechaFinReserva, @RequestParam(required = false) String[] mensualReserva,
            @RequestParam(required = false) String[] diarioReserva, @RequestParam(required = false) String[] porFechaReserva, Usuario logueado) throws Exception {

        propiedadServicio.modificarPropiedad(id, titulo, descripcion, tipo, serviciosInput, imagenesInput, valor, imagenesViejas, estado,
                pais, provincia, departamento, localidad, calle, numeracion, observaciones, latitud, longitud,
                fechaInicioReserva, fechaFinReserva, mensualReserva, diarioReserva, porFechaReserva, logueado);

        return "index.html";
    }

    @PreAuthorize("hasRole('ROLE_PROPIETARIO')")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, HttpSession session) throws MiException, PermisosException {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        Propiedad propiedad = propiedadServicio.getOne(id);
        if (!propietarioServicio.comprobarPropietario(logueado, propiedad)) {
            throw new PermisosException("No es posible eliminar la propiedad porque no te pertenece");
        }
        propiedadServicio.eliminar(id, logueado);
        return "index.html";
    }

    @GetMapping("/puntuacion/{id}")
    public ModelAndView puntuacion(@PathVariable String id, ModelMap modelo) {
        Double promedioPuntuacion = comentarioServicio.obtenerPromedioPuntuacionPorPropiedad(id);
        modelo.addAttribute("promedioPuntuacion", promedioPuntuacion);
        return new ModelAndView("puntuacion.html", modelo);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/baja")
    public ModelAndView bajaPropiedad(@RequestParam String idPropiedad, ModelMap modelo) throws MiException {
        propiedadServicio.darDeBaja(idPropiedad);
        return new ModelAndView("redirect:/admin/dashboard", modelo);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/alta")
    public ModelAndView altaPropiedad(@RequestParam String idPropiedad, ModelMap modelo) throws MiException {
        propiedadServicio.darDeAlta(idPropiedad);
        return new ModelAndView("redirect:/admin/dashboard", modelo);
    }

}
