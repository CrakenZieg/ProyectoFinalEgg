
package com.EquipoB.AsadoYPileta.repositorios;
import com.EquipoB.AsadoYPileta.entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepositorio extends JpaRepository <Reserva,String> {
    
    @Query("SELECT r FROM Reserva r WHERE r.disponible=1 AND r.usuario.id = :id")
    public boolean buscarReservaCliente(@Param("id") String id);
    
    @Query("SELECT r FROM Reserva r WHERE r.disponible=1 AND r.propiedad.id = :id")
    public boolean buscarReservaPropiedad(@Param("id") String id);
    
}
