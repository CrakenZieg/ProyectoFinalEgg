
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
    public TipoContacto getOne(String tipo) {
        return tipoContactoRepositorio.buscarPorTipo(tipo);
    }
    
    @Transactional
    public List<TipoContacto> listarTipoContacto() {
        List<TipoContacto> reservas = new ArrayList();
        reservas = tipoContactoRepositorio.findAll();
        return reservas;
    }
    
}
