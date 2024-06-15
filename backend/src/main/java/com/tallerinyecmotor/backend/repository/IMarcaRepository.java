package com.tallerinyecmotor.backend.repository;

import com.tallerinyecmotor.backend.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMarcaRepository extends JpaRepository<Marca,Long> {

}
