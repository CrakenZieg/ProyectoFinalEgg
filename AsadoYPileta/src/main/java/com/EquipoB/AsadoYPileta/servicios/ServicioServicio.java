
package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Servicio;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.PropiedadRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.EquipoB.AsadoYPileta.repositorios.ServicioRepositorio;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioServicio {
    
    @Autowired
    private ServicioRepositorio servicioRepositorio;
    
    @Autowired
    private PropiedadRepositorio propiedadRepositorio;
    
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
    public void modificarServicio(String id, String tipoComodidad, Double valor) throws MiException {

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

        if ( tipoComodidad == null || tipoComodidad.trim().isEmpty() ) {

            throw new MiException("El tipo de comodidad no puede ser nulo o estar vacio");
        }
        
        if (valor == null) {

            throw new MiException("El valor no puede ser nulo");
        }

    }
    
    public Servicio getOne(String id) {
        return servicioRepositorio.getOne(id);    
    }
    
    public Servicio servicioPorTipo(String tipo){
        return servicioRepositorio.buscarTipoServicio(tipo);
    }
    
    public List<Servicio> listarServiciosArray(String[] serviciosInput){
        List<Servicio> servicios = new ArrayList<>();            
        for (String servicioElem : serviciosInput) {
            Servicio servicio = servicioPorTipo(servicioElem);
            servicios.add(servicio);
        }
        return servicios;
    }
    
    @Transactional
    public void eliminarServicio(String id) throws MiException{
        if(!propiedadRepositorio.buscarPorServicio(id).isEmpty()){
            throw new MiException("No se puede eliminar el servicio si está siendo utilizado.");
        } else {
            servicioRepositorio.deleteById(id);    
        }
    }
    
}


