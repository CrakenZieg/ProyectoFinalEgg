
package com.EquipoB.AsadoYPileta.repositorios;

import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PropiedadRepositorio extends JpaRepository<Propiedad, String> {
    
    @Query("SELECT p FROM Propiedad p WHERE p.tipo = :tipo")
    public List<Propiedad> buscarPorTipo(@Param("tipo") String tipo);
    
    @Query("SELECT p FROM Propiedad p JOIN p.servicios s WHERE s.id = :idServicio")
    public List<Propiedad> buscarPorServicio(@Param("idServicio") String idServicio);
    
    @Query("SELECT COUNT(p) FROM Propiedad p WHERE p.estado=true")
    public int buscarCuantasPropiedades();
    
    
}
