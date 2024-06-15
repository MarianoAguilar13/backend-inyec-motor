package com.tallerinyecmotor.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @Entity
public class TipoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nombre;
    @ManyToMany(mappedBy = "tipos")
    @JsonManagedReference
    private List<Producto> productos;

    public TipoProducto(){

    }

    public TipoProducto(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
