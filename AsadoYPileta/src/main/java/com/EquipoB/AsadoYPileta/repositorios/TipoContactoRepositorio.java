
package com.EquipoB.AsadoYPileta.repositorios;

import com.EquipoB.AsadoYPileta.entidades.TipoContacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoContactoRepositorio extends JpaRepository<TipoContacto, String>{
    
    /**
     *@param tipo: el tipo de contacto
     *@return TipoContacto asociado
     */
    @Query("SELECT t FROM TipoContacto t WHERE t.tipo = :tipo")
    public TipoContacto buscarPorTipo(@Param("tipo") String tipo);
    
}
