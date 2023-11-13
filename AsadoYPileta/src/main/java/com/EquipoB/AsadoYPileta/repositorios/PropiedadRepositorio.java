
package com.EquipoB.AsadoYPileta.repositorios;

import com.EquipoB.AsadoYPileta.entidades.Propiedad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PropiedadRepositorio extends JpaRepository<Propiedad, String> {
    
    /**
     *Query para recuperar las propiedades de un tipo particular
     *@param tipo: string del tipo de propiedad
     *@return lista con las propiedades de ese tipo
    */
    @Query("SELECT p FROM Propiedad p WHERE p.tipo = :tipo")
    public List<Propiedad> buscarPorTipo(@Param("tipo") String tipo);

    /**
     *Query para recuperar las propiedades con un servicio particular
     *@param idServicio: id del servicio
     *@return lista con las propiedades con ese servicio
    */
    @Query("SELECT p FROM Propiedad p JOIN p.servicios s WHERE s.id = :idServicio")
    public List<Propiedad> buscarPorServicio(@Param("idServicio") String idServicio);
    
    /**
     *Query para recuperar el promedio de las puntuaciones de una propiedad
     *(la puntuacion es parte del comentario)
     *@param idPropiedad: id de la propiedad
     *@return double con el resultador
    */
    @Query("SELECT AVG(c.puntuacion) " +
           "FROM Propiedad p " +
           "JOIN Comentario c ON p.id = c.propiedad.id " +
           "WHERE p.id = :idPropiedad ")
    public double obtenerPromedioPuntuacionPorPropiedad(@Param("idPropiedad") String idPropiedad);

    /**
     *Query para recuperar las propiedades activas
     *@return lista las propiedades
    */
    @Query("SELECT COUNT(p) FROM Propiedad p WHERE p.estado=true")
    public int buscarCuantasPropiedades();
    
     @Query("SELECT p FROM Propiedad p WHERE p.estado=true")
    public List<Propiedad> buscarPropiedadesActivas();

    
}
