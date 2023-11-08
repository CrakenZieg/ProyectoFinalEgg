
package com.EquipoB.AsadoYPileta.repositorios;

import com.EquipoB.AsadoYPileta.entidades.Comentario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, String>{
     
    @Query("SELECT c FROM Comentario c WHERE c.Usuario.id IN :id")
    public List<Comentario> buscarPorCliente(@Param("id") String id);
    
    @Query("SELECT c FROM Comentario c WHERE c.Propiedad.id IN :id")
    public List<Comentario> buscarPorPropiedad(@Param("id") String id);
    
}
