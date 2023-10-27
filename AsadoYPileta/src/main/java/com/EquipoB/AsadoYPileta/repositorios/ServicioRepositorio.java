
package com.EquipoB.AsadoYPileta.repositorio;

import com.EquipoB.AsadoYPileta.entidades.Servicio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepositorio extends JpaRepository<Servicio, String> {
    
    @Query("SELECT s FROM Servicio s WHERE s.tipoComodidad = :tipoComodidad")
    public Servicio buscarPorTipoServicio(@Param("tipoComodidad") String tipoComodidad);
    
    @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :nombre")
    public List<Servicio> buscarPorAutor(@Param("nombre") String nombre);
    
}
