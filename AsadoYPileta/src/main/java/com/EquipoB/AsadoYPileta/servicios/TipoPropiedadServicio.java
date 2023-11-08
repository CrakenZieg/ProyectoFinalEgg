
package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.TipoPropiedad;
import com.EquipoB.AsadoYPileta.repositorios.TipoPropiedadRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoPropiedadServicio {
    
    @Autowired
    private TipoPropiedadRepositorio tipoPropiedadRepositorio;
    
    @Transactional
    public void crearTipoPropiedad(String tipo, String titulo, String emoji, String descripcion){
        TipoPropiedad tipoPropiedad = new TipoPropiedad();
        tipoPropiedad.setTipo(tipo);
        tipoPropiedad.setTitulo(titulo);
        tipoPropiedad.setEmoji(emoji);
        tipoPropiedad.setDescripcion(descripcion);        
        tipoPropiedadRepositorio.save(tipoPropiedad);
    }
    
    @Transactional
    public void modificarTipoPropiedad(String id, String tipo, String titulo, String emoji, String descripcion){
        TipoPropiedad tipoPropiedad = tipoPropiedadRepositorio.getById(id);
        tipoPropiedad.setTipo(tipo);
        tipoPropiedad.setTitulo(titulo);
        tipoPropiedad.setEmoji(emoji);
        tipoPropiedad.setDescripcion(descripcion);        
        tipoPropiedadRepositorio.save(tipoPropiedad);
    }
    
    @Transactional
    public void eliminarTipoPropiedad(String id){
        TipoPropiedad tipoPropiedad = tipoPropiedadRepositorio.getById(id);
        tipoPropiedadRepositorio.delete(tipoPropiedad);
    }
    
    public TipoPropiedad getOne(String id){
        return tipoPropiedadRepositorio.getById(id);
    }
    
    public TipoPropiedad getOnePorTipo(String tipo){
        return tipoPropiedadRepositorio.buscarPorTipo(tipo);
    }
    
    public List<TipoPropiedad> listarTipoPropiedad(){
        return tipoPropiedadRepositorio.findAll();
    }
    
}
