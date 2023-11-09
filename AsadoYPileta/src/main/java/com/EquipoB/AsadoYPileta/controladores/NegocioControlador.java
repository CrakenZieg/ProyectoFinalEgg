package com.EquipoB.AsadoYPileta.controladores;

import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.servicios.ServicioServicio;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/registroServicio")
    public ModelAndView registroServicio(@RequestParam String tipoComodidad, @RequestParam Double valor, ModelMap modelo) {
        try {
            servicioServicio.crearSercicio(tipoComodidad, valor);
            modelo.put("exito", "La comodidad fue cargado correctamente");            
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return new ModelAndView("forward:/admin/dashboard",modelo);
    }    
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/modificarServicio")
    public ModelAndView modificarServicio(@RequestParam String id, @RequestParam String tipoComodidad, @RequestParam Double valor, ModelMap modelo) throws MiException {
        try {
            servicioServicio.modificarServicio(id, tipoComodidad, valor);
            modelo.put("exito", "La comodidad fue editada correctamente");            
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return new ModelAndView("forward:/admin/dashboard",modelo);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/eliminarServicio")
    public ModelAndView eliminarServicio(@RequestParam String id, ModelMap modelo) throws MiException {
        try {            
            servicioServicio.eliminarServicio(id);
            modelo.put("exito", "La comodidad fue eliminada correctamente");            
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return new ModelAndView("forward:/admin/dashboard",modelo);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/registroTipoPropiedad")
    public ModelAndView registro(@RequestParam String tipoComodidad, @RequestParam Double valor, ModelMap modelo) {
        try {
            servicioServicio.crearSercicio(tipoComodidad, valor);
            modelo.put("exito", "La comodidad fue cargado correctamente");            
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return new ModelAndView("forward:/admin/dashboard",modelo);
    }    
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/modificarTipoPropiedad")
    public ModelAndView modificar(@RequestParam String id, @RequestParam String tipoComodidad, @RequestParam Double valor, ModelMap modelo) throws MiException {
        try {
            servicioServicio.modificarServicio(id, tipoComodidad, valor);
            modelo.put("exito", "La comodidad fue editada correctamente");            
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return new ModelAndView("forward:/admin/dashboard",modelo);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/eliminarTipoPropiedad")
    public ModelAndView eliminar(@RequestParam String id, ModelMap modelo) throws MiException {
        try {            
            servicioServicio.eliminarServicio(id);
            modelo.put("exito", "La comodidad fue eliminada correctamente");            
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return new ModelAndView("forward:/admin/dashboard",modelo);
    }
    
}
