
package com.EquipoB.AsadoYPileta.repositorios;

import com.EquipoB.AsadoYPileta.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String>{
    
}
