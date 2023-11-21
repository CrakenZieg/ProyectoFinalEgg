
package com.EquipoB.AsadoYPileta.repositorios;

import com.EquipoB.AsadoYPileta.entidades.TipoPropiedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoPropiedadRepositorio extends JpaRepository<TipoPropiedad, String>{
    
    /**
     *@param tipo: el tipo de propiedad
     *@return TipoPropiedad asociado
     */
    @Query("SELECT t FROM TipoPropiedad t WHERE t.tipo = :tipo")
    public TipoPropiedad buscarPorTipo(@Param("tipo") String tipo);
    
}
