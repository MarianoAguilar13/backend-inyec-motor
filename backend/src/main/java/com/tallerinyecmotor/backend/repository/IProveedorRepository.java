package com.tallerinyecmotor.backend.repository;

import com.tallerinyecmotor.backend.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProveedorRepository extends JpaRepository<Proveedor,Long> {
}
