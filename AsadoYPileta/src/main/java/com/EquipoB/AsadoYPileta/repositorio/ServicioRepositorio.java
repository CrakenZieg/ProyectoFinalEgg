
package com.EquipoB.AsadoYPileta.repositorio;

import com.EquipoB.AsadoYPileta.entidades.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepositorio extends JpaRepository<Servicio, String> {
        
}
