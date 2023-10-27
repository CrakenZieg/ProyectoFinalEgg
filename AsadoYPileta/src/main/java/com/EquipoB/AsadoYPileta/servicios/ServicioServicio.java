
package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Servicio;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.ServicioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioServicio {
    
    @Autowired
    private ServicioRepositorio servicioRepositorio;
    
    @Transactional
    public void crearSercicio(String tipoComodidad, Double valor) throws MiException{
        
        validar(tipoComodidad, valor);
        
        Servicio servicio = new Servicio();
        
        servicio.setTipoComodidad(tipoComodidad);
        servicio.setValor(valor);
        
        servicioRepositorio.save(servicio);
    
    }
    
    public List<Servicio> listarServicios() {

        List<Servicio> servicios = new ArrayList();

        servicios = servicioRepositorio.findAll();

        return servicios;
    }
    
    @Transactional
    public void modificarServicio(String tipoComodidad, String id, Double valor) throws MiException {

        validar(tipoComodidad, valor);

        Optional<Servicio> respuesta = servicioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Servicio servicio = respuesta.get();

            servicio.setTipoComodidad(tipoComodidad);
            servicio.setValor(valor);

            servicioRepositorio.save(servicio);

        }
    }
    
    private void validar(String tipoComodidad, Double valor) throws MiException {

        if (tipoComodidad.isEmpty() || tipoComodidad == null) {

            throw new MiException("El tipo de comodidad no puede ser nulo o estar vacio");
        }
        
        if (valor == null) {

            throw new MiException("El valor no puede ser nulo");
        }

    }
    
    public Servicio getOne(String id) {

        return servicioRepositorio.getOne(id);
    
    }
    
    
    @Transactional
    public void eliminarServicio(String id, String tipoComodidad, Double valor) throws MiException{
    
        try {
            validar(tipoComodidad, valor);
            servicioRepositorio.deleteById(id);
            
        } catch (MiException ex) {
            ex.getMessage();
        }
    
    }
    
    
    
    
}


