
package com.EquipoB.AsadoYPileta.repositorios;
import com.EquipoB.AsadoYPileta.entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepositorio extends JpaRepository <Reserva,String> {
    
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Reserva r WHERE r.disponible=true AND r.usuario.id = :id")
    public boolean buscarReservaCliente(@Param("id") String id);
    
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Reserva r WHERE r.disponible=true AND r.propiedad.id = :id")
    public boolean buscarReservaPropiedad(@Param("id") String id);
    
}
