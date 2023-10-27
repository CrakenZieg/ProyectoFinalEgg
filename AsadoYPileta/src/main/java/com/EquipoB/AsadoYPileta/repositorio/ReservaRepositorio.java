
package com.EquipoB.AsadoYPileta.repositorio;

import com.EquipoB.AsadoYPileta.entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepositorio extends JpaRepository <Reserva,String> {
    
    
    
}
