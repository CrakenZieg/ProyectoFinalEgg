
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
    
    /**
     *Devuelve un booleano en funcion de si el cliente tiene reservas activas
     *@param id: id del cliente
     *@return boolean
     */
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Reserva r WHERE r.disponible=true AND r.cliente.id = :id")
    public boolean buscarReservaCliente(@Param("id") String id);
    
    /**
     *Devuelve un booleano en funcion de si la propiedad tiene reservas activas
     *@param id: id de la propiedad
     *@return boolean
     */
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Reserva r WHERE r.disponible=true AND r.propiedad.id = :id") 
    public boolean buscarReservaPropiedad(@Param("id") String id);
    
    /**
     *Este metodo tiene un parametro pero no lo utiliza en la query
     *@param fechaFin
     *@return lista de reservas
     */
    @Query("SELECT r FROM Reserva r WHERE r.disponible = true AND r.fechaFin <= CURRENT_DATE")
    public List<Reserva> buscarFinReserva(@Param("fechaFin") Date fechaFin);
   
    @Query("SELECT r FROM Reserva r WHERE r.disponible=true AND r.fechaFin<'fechaActual'") 
    public List <Reserva> buscarFinalizadas(@Param("fechaActual") String fechaActual);    

    @Query("SELECT r.fechaInicio FROM Reserva r WHERE r.propiedad.id=id") 
    public List <Date> buscarFechaInicioReserva(@Param("id") String id);
    
    @Query("SELECT r.fechaFin FROM Reserva r WHERE r.propiedad.id=id") 
    public List <Date> buscarFechaFinReserva(@Param("id") String id);

    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.fechaInicio <= CURRENT_DATE") 
    public int buscarCuantasReservasActivas();

    /**
     * Devuelve reservas futuras por id de propiedad
     * @param idpropiedad
     * @return 
     */
    @Query("SELECT r FROM Reserva r WHERE r.propiedad.id = :idPropiedad AND r.fechaFin > CURRENT_DATE")
    public List<Reserva> buscarReservaPorPropiedad(@Param("idPropiedad") String idpropiedad);
    
    @Query("SELECT r FROM Reserva r WHERE r.cliente.id = :idCliente")
    public List<Reserva> buscarReservaPorCliente(@Param("idCliente") String idCliente);
    //esto sirve para mostrar las reservas asociadas a una propiedad en el perfil
    @Query("SELECT r FROM Reserva r WHERE r.propiedad.id IN :idPropiedad")
    public List<Reserva> buscarReservaPorPropiedadPerfil(@Param("idPropiedad") List<String> idPropiedad);

}
