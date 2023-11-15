
package com.EquipoB.AsadoYPileta.repositorios;

import com.EquipoB.AsadoYPileta.entidades.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepositorio extends JpaRepository<Servicio, String> {
       
    /**
     *@param tipoComodidad: el tipo de servicio
     *@return Servicio asociado
     */
    @Query("SELECT s FROM Servicio s WHERE s.tipoComodidad = :tipoComodidad")
    public Servicio buscarTipoServicio(@Param("tipoComodidad") String tipoComodidad);
    
}
