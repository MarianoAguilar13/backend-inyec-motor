package com.tallerinyecmotor.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Getter @Setter @Entity
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nombre;
    @Column(nullable = false)
    @DecimalMin("1.0")
    @DecimalMax("5.0")
    private double motorLitros;
    @Column(nullable = false)
    @Size(min = 3, max = 50)
    private String motorTipo;
    @Column(nullable = false)
    @Range(min = 1950, max = 2040)
    private int anio;
    @ManyToOne
    @JoinColumn(name="marca_id")
    @JsonBackReference
    private Marca marca;
    @ManyToMany (mappedBy = "modelos")
    @JsonManagedReference
    private List<Producto> productos;

    public Modelo(){

    }

    public Modelo(Long id, String nombre, double motorLitros, String motorTipo, int anio, Marca marca) {
        this.id = id;
        this.nombre = nombre;
        this.motorLitros = motorLitros;
        this.motorTipo = motorTipo;
        this.anio = anio;
        this.marca = marca;
    }
}
