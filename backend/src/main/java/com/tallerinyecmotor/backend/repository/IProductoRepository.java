package com.tallerinyecmotor.backend.repository;


import com.tallerinyecmotor.backend.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends JpaRepository<Producto,Long> {

}
