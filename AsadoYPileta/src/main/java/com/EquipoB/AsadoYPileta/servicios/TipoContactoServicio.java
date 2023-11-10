
package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.TipoContacto;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
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
    public void crearTipoContacto(String tipo) throws MiException{
        TipoContacto tipoPropiedad = new TipoContacto();
        tipoPropiedad.setTipo(tipo);  
        tipoContactoRepositorio.save(tipoPropiedad);
    }
    
    @Transactional
    public void modificarTipoContacto(String id, String tipo) throws MiException{
        TipoContacto tipoPropiedad = tipoContactoRepositorio.getById(id);
        tipoPropiedad.setTipo(tipo);       
        tipoContactoRepositorio.save(tipoPropiedad);
    }
    
    @Transactional
    public void eliminarTipoContacto(String id) throws MiException{
        TipoContacto tipoPropiedad = tipoContactoRepositorio.getById(id);
        tipoContactoRepositorio.delete(tipoPropiedad);
    }
    
    public TipoContacto getOne(String id) throws MiException{
        return tipoContactoRepositorio.getById(id);
    }
    
    public TipoContacto getOnePorTipo(String tipo) throws MiException{
        return tipoContactoRepositorio.buscarPorTipo(tipo);
    }
    
    public List<TipoContacto> listarTipoContacto() {
        List<TipoContacto> contactos = new ArrayList();
        contactos = tipoContactoRepositorio.findAll();
        return contactos;
    }
    
    
}
