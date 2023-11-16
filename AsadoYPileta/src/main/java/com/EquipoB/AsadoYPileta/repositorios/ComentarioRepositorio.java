
package com.EquipoB.AsadoYPileta.repositorios;

import com.EquipoB.AsadoYPileta.entidades.Comentario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, String>{
     
    @Query("SELECT c FROM Comentario c WHERE c.usuario.id IN :id") 
    public List<Comentario> buscarPorCliente(@Param("id") String id); // Devuelve una lista de comentarios asociados al usuario con el ID proporcionado.
    
    @Query("SELECT c FROM Comentario c WHERE c.propiedad.id IN :id")
    public List<Comentario> buscarPorPropiedad(@Param("id") String id); // Devuelve una lista de comentarios asociados a la propiedad con el ID proporcionado.
    
}
