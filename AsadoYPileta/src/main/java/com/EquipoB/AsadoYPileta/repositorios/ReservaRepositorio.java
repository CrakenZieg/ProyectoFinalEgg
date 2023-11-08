
package com.EquipoB.AsadoYPileta.repositorios;
import com.EquipoB.AsadoYPileta.entidades.Reserva;
import java.util.Date;
import java.util.List;
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
    
    
    @Query("SELECT r FROM Reserva r WHERE r.disponible = true AND r.fechaFin <= CURRENT_DATE") // CURRENT_DATE es una forma de obtener la fecha actual desde la base de datos y usarla en tus consultas
    List <Reserva> buscarFinReserva(@Param("fechaFin") Date fechaFin); // diseñado para buscar y devolver una lista de reservas que han finalizado, lo que permite trabajar con esas reservas de manera individual o colectiva.

   
   
    @Query("SELECT r FROM Reserva r WHERE r.disponible=true AND r.fechaFin<'fechaActual'") 
    List <Reserva> buscarFinalizadas(@Param("fechaActual") String fechaActual);

}
