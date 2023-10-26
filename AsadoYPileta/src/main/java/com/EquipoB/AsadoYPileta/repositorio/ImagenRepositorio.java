
package com.EquipoB.AsadoYPileta.repositorio;

import com.EquipoB.AsadoYPileta.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tamara
 */
@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String>{
    
}
