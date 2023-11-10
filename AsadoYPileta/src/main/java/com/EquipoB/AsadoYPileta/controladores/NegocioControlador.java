package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.servicios.ServicioServicio;
import com.EquipoB.AsadoYPileta.servicios.TipoContactoServicio;
import com.EquipoB.AsadoYPileta.servicios.TipoPropiedadServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/negocio")
public class NegocioControlador {

    @Autowired
    private ServicioServicio servicioServicio;
    
    @Autowired
    private TipoPropiedadServicio tipoPropiedadServicio;
    
    @Autowired
    private TipoContactoServicio tipoContactoServicio;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/registroServicio")
    public ModelAndView registroServicio(@RequestParam String tipoServicio, 
            @RequestParam Double servicioValor, ModelMap modelo) {
        try {
            servicioServicio.crearSercicio(tipoServicio, servicioValor);   
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return new ModelAndView("redirect:/admin/dashboard",modelo);
    }    
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/modificarServicio")
    public ModelAndView modificarServicio(@RequestParam String idServicio, @RequestParam String tipoServicio, 
            @RequestParam Double servicioValor, ModelMap modelo) throws MiException {
        try {
            servicioServicio.modificarServicio(idServicio, tipoServicio, servicioValor);       
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return new ModelAndView("redirect:/admin/dashboard",modelo);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/eliminarServicio")
    public ModelAndView eliminarServicio(@RequestParam String idServicio, ModelMap modelo) throws MiException {
        try {            
            servicioServicio.eliminarServicio(idServicio);         
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return new ModelAndView("redirect:/admin/dashboard",modelo);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/registroTipoPropiedad")
    public ModelAndView registroTipoPropiedad(@RequestParam String tipoPropiedadTipo, @RequestParam String tipoPropiedadTitulo,
            @RequestParam String tipoPropiedadEmoji, @RequestParam String tipoPropiedadDescripcion, ModelMap modelo) {
        try {
            tipoPropiedadServicio.crearTipoPropiedad(tipoPropiedadTipo, tipoPropiedadTitulo,
                    tipoPropiedadEmoji, tipoPropiedadDescripcion);         
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return new ModelAndView("redirect:/admin/dashboard",modelo);
    }    
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/modificarTipoPropiedad")
    public ModelAndView modificarTipoPropiedad(@RequestParam String idTipoPropiedad, @RequestParam String tipoPropiedadTipo,
            @RequestParam String tipoPropiedadTitulo, @RequestParam String tipoPropiedadEmoji, 
            @RequestParam String tipoPropiedadDescripcion, ModelMap modelo) {
        try {
            tipoPropiedadServicio.modificarTipoPropiedad(idTipoPropiedad, tipoPropiedadTipo, tipoPropiedadTitulo,
                    tipoPropiedadEmoji, tipoPropiedadDescripcion);            
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return new ModelAndView("redirect:/admin/dashboard",modelo);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/eliminarTipoPropiedad")
    public ModelAndView eliminarTipoPropiedad(@RequestParam String idTipoPropiedad, ModelMap modelo) throws MiException {
        try {            
            tipoPropiedadServicio.eliminarTipoPropiedad(idTipoPropiedad);            
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return new ModelAndView("redirect:/admin/dashboard",modelo);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/registroTipoContacto")
    public ModelAndView registroTipoContacto(@RequestParam String tipoContactoTipo, ModelMap modelo) {
        try {
            tipoContactoServicio.crearTipoContacto(tipoContactoTipo);         
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return new ModelAndView("redirect:/admin/dashboard",modelo);
    }    
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/modificarTipoContacto")
    public ModelAndView modificarTipoContacto(@RequestParam String idTipoContacto, 
            @RequestParam String tipoContactoTipo, ModelMap modelo) {
        try {
            tipoContactoServicio.modificarTipoContacto(idTipoContacto, tipoContactoTipo);            
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return new ModelAndView("redirect:/admin/dashboard",modelo);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/eliminarTipoContacto")
    public ModelAndView eliminarTipoContacto(@RequestParam String idTipoContacto, ModelMap modelo) throws MiException {
        try {            
            tipoContactoServicio.eliminarTipoContacto(idTipoContacto);            
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return new ModelAndView("redirect:/admin/dashboard",modelo);
    }
}
