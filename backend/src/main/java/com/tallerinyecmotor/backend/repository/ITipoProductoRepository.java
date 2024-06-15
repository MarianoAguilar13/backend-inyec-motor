package com.tallerinyecmotor.backend.repository;

import com.tallerinyecmotor.backend.model.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoProductoRepository extends JpaRepository<TipoProducto,Long> {
}
