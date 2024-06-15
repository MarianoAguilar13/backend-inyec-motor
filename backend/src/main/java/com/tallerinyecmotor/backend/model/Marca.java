package com.tallerinyecmotor.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@Entity
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 50)
    private String nombre;
    @ManyToMany(mappedBy = "marcas")
    @JsonManagedReference
    private List<Producto> productos;
    @OneToMany(mappedBy = "marca")
    @JsonManagedReference
    private List<Modelo> modelos;

    public Marca(){

    }

    public Marca(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
