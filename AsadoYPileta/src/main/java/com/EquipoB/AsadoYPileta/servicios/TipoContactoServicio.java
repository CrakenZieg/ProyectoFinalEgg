
package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.TipoContacto;
import com.EquipoB.AsadoYPileta.repositorios.TipoContactoRepositorio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoContactoServicio {
    
    @Autowired
    private TipoContactoRepositorio tipoContactoRepositorio;    
    
    @Transactional
    public void crearTipoContacto(String tipo){
        TipoContacto tipoPropiedad = new TipoContacto();
        tipoPropiedad.setTipo(tipo);  
        tipoContactoRepositorio.save(tipoPropiedad);
    }
    
    @Transactional
    public void modificarTipoContacto(String id, String tipo){
        TipoContacto tipoPropiedad = tipoContactoRepositorio.getById(id);
        tipoPropiedad.setTipo(tipo);       
        tipoContactoRepositorio.save(tipoPropiedad);
    }
    
    @Transactional
    public void eliminarTipoContacto(String id){
        TipoContacto tipoPropiedad = tipoContactoRepositorio.getById(id);
        tipoContactoRepositorio.delete(tipoPropiedad);
    }
    
    public TipoContacto getOne(String id){
        return tipoContactoRepositorio.getById(id);
    }
    
    public TipoContacto getOnePorTipo(String tipo){
        return tipoContactoRepositorio.buscarPorTipo(tipo);
    }
    
    public List<TipoContacto> listarTipoContacto() {
        List<TipoContacto> reservas = new ArrayList();
        reservas = tipoContactoRepositorio.findAll();
        return reservas;
    }
    
    
}
