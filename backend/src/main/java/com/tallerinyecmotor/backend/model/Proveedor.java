package com.tallerinyecmotor.backend.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @Entity
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @Size(min = 8, max = 30)
    private String cuit;
    @Column(nullable = false)
    @Size(min = 8, max = 25)
    private String tel;
    @Column(nullable = false)
    @Size(min = 10, max = 255)
    private String direccion;
    @Column(nullable = false)
    @Size(min = 3, max = 255)
    private String nombre;
    @Column(nullable = false, unique = true)
    @Email
    private String email;
    @ManyToMany (mappedBy = "proveedores")
    @JsonManagedReference
    private List<Producto> productos;

    public Proveedor() {

    }

    public Proveedor(Long id, String cuit, String tel, String direccion, String nombre, String email) {
        this.id = id;
        this.cuit = cuit;
        this.tel = tel;
        this.direccion = direccion;
        this.nombre = nombre;
        this.email = email;
    }
}
