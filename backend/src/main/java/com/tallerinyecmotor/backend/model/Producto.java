package com.tallerinyecmotor.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter @Setter @Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @Size(min = 6, max = 25)
    private String codigo;
    @Column(nullable = false)
    @Size(min = 3, max = 255)
    private String nombre;
    @Column(nullable = false)
    private int stockMin;
    @Column(nullable = false)
    private int stockMax;
    @Column(nullable = false)
    private int stockActual;
    @Column(nullable = false)
    @DecimalMin("0.0")
    private double precioVenta;
    @Column(nullable = false)
    @DecimalMin("0.0")
    private double precioCosto;
    @ManyToMany
    @JsonBackReference
    private List<Proveedor> proveedores;
    @ManyToMany
    @JsonBackReference
    private List<TipoProducto> tipos;
    @ManyToMany
    @JsonBackReference
    private List<Modelo> modelos;

    public Producto(){

    }

    public Producto(Long id, String codigo, String nombre, int stockMin, int stockMax, int stockActual, double precioVenta, double precioCosto, List<Proveedor> proveedores, List<TipoProducto> tipos, List<Modelo> modelos) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.stockMin = stockMin;
        this.stockMax = stockMax;
        this.stockActual = stockActual;
        this.precioVenta = precioVenta;
        this.precioCosto = precioCosto;
        this.proveedores = proveedores;
        this.tipos = tipos;
        this.modelos = modelos;
    }

    //filtros - aceites - bujias - baterias
}