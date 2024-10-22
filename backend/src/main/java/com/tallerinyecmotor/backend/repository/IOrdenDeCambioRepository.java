package com.tallerinyecmotor.backend.repository;

import com.tallerinyecmotor.backend.model.Modelo;
import com.tallerinyecmotor.backend.model.OrdenDeCambio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOrdenDeCambioRepository extends JpaRepository<OrdenDeCambio,Long> {

    @Query("SELECT o FROM OrdenDeCambio o WHERE o.patente LIKE %:patente%")
    List<OrdenDeCambio> findByPatente(@Param("patente") String patente);

}
