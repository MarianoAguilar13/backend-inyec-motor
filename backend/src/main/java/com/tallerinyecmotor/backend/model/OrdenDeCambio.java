package com.tallerinyecmotor.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OrdenDeCambio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Size(min = 6, max = 25)
    private String nombreCompleto;
    @Column(nullable = false)
    @Size(min = 4, max = 10)
    private String patente;
    @DecimalMin("0.0")
    private double manoDeObra;
    @Column(nullable = true)
    @DecimalMin("0.0")
    private double otros;
    @Column(nullable = true)
    @Size(max = 255)
    private String descripcionOtros;
    @Column(nullable = false)
    @DecimalMin("0.0")
    private double precioTotal;
    @ManyToMany
    @JsonManagedReference
    private List<Producto> productos;
    @ManyToOne
    @JsonManagedReference
    private Modelo modelo;


    public void calcularPrecioTotal(){

        double sumaProductos = 0;

        if (!this.productos.isEmpty()){
            for (Producto producto: this.productos) {
                sumaProductos = sumaProductos + producto.getPrecioVenta();
            }
        }

        double precioTotal = this.manoDeObra + this.otros + sumaProductos;

        this.setPrecioTotal(precioTotal);
    }

}
