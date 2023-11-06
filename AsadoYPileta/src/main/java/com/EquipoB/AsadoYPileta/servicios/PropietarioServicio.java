package com.EquipoB.AsadoYPileta.servicios;

import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import com.EquipoB.AsadoYPileta.entidades.Propietario;
import com.EquipoB.AsadoYPileta.excepciones.MiException;
import com.EquipoB.AsadoYPileta.repositorios.PropietarioRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropietarioServicio {

    @Autowired
    private PropietarioRepositorio propietarioRepositorio;

    @Transactional
    public void agregarPropiedades(String id, Propiedad propiedad) throws MiException {
        Optional<Propietario> respuesta = propietarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Propietario propietario = respuesta.get();
            propietario.getPropiedades().add(propiedad);
            propietarioRepositorio.save(propietario);
        } else {
            throw new MiException("No se encontro el usuario");
        }
    }
}
