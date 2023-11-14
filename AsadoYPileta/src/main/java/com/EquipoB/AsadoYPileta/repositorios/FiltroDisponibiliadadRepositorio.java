
package com.EquipoB.AsadoYPileta.repositorios;

import com.EquipoB.AsadoYPileta.entidades.FiltroDisponibilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FiltroDisponibiliadadRepositorio extends JpaRepository<FiltroDisponibilidad, String> {
    
}
