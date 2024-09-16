package com.tallerinyecmotor.backend.repository;

import com.tallerinyecmotor.backend.model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IModeloRepository extends JpaRepository<Modelo,Long> {
    /* Con esta query nos trarmos todos los modelos que tengan en alguna parte de su nombre, el string nombre */

    @Query("SELECT m FROM Modelo m WHERE m.nombre LIKE %:nombre%")
    List<Modelo> findByNombre(@Param("nombre") String nombre);

}
