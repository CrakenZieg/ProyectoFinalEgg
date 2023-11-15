
package com.EquipoB.AsadoYPileta.repositorios;

import com.EquipoB.AsadoYPileta.entidades.Contacto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoRepositorio extends JpaRepository<Contacto, String>{
    
    /**
     *Query para recuperar los contactos de un tipo particular
     *@param idTipoContacto: id del tipo de contacto
     *@return lista con los contactos de ese tipo
    */
    @Query("SELECT c FROM Contacto c JOIN c.tipo t WHERE t.id = :idTipoContacto")
    public List<Contacto> buscarPorTipo(@Param("idTipoContacto") String idTipoContacto);
    
}
