
package com.EquipoB.AsadoYPileta.repositorios;

import com.EquipoB.AsadoYPileta.entidades.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface PropietarioRepositorio  extends JpaRepository<Propietario, String>{

    @Query("SELECT COUNT(p) FROM Propietario p WHERE p.cliente.usuario.alta=true")
    public int buscarCuantosPropietarios();
    
}
