
package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.repositorios.ClienteRepositorio;
import com.EquipoB.AsadoYPileta.repositorios.PropiedadRepositorio;
import com.EquipoB.AsadoYPileta.repositorios.PropietarioRepositorio;
import com.EquipoB.AsadoYPileta.repositorios.ReservaRepositorio;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServicio {
    
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    @Autowired
    private PropietarioRepositorio propietarioRepositorio;
    
    @Autowired
    private PropiedadRepositorio propiedadRepositorio;
    
    @Autowired
    private ReservaRepositorio reservaRepositorio;
    
    public Map<String,Integer> datosDeUso(){
        Map<String,Integer> datos = new HashMap<>();
        datos.put("numeroClientes", clienteRepositorio.buscarCuantosClientes());
        datos.put("numeroPropietarios", propietarioRepositorio.buscarCuantosPropietarios());
        datos.put("numeroPropiedades", propiedadRepositorio.buscarCuantasPropiedades());
        datos.put("numeroReservas", reservaRepositorio.buscarCuantasReservasActivas());
        return datos;
    }
    
}
