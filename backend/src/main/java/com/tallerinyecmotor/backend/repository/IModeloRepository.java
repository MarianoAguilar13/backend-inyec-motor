package com.tallerinyecmotor.backend.repository;

import com.tallerinyecmotor.backend.model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IModeloRepository extends JpaRepository<Modelo,Long> {

}
