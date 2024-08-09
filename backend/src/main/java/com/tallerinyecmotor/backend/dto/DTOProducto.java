package com.tallerinyecmotor.backend.dto;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter @Getter
public class DTOProducto {
    @NotNull(message = "El id no puede ser nula")
    @Positive(message = "El id debe ser número positivo")
    private Long id;
    @NotNull(message = "El Código no puede ser nulo")
    @Size(min = 6, max = 25,message = "El código debe tener entre 6 y 25 caracteres")
    private String codigo;
    @NotNull(message = "El Nombre no puede ser nulo")
    @Size(min = 3, max = 255,message = "El nombre debe tener entre 3 y 255 caracteres")
    private String nombre;
    @NotNull(message = "El stock minimo no puede ser nulo")
    @Positive(message = "El stock mínimo debe ser un número positivo.")
    private int stockMin;
    @NotNull(message = "El stock máximo no puede ser nulo")
    @Positive(message = "El stock máximo debe ser un número positivo.")
    private int stockMax;
    @NotNull(message = "El stock actual no puede ser nulo")
    @Positive(message = "El stock actual debe ser un número positivo.")
    private int stockActual;
    @NotNull(message = "El precio de venta no puede ser nulo")
    @Positive(message = "El precio de venta debe ser un número positivo.")
    private double precioVenta;
    @NotNull(message = "El precio de costo no puede ser nulo")
    @Positive(message = "El precio de costo debe ser un número positivo.")
    private double precioCosto;
    @NotEmpty(message = "El producto debe tener uno o mas proveedores.")
    private List<Long> proveedores;
    @NotEmpty(message = "El producto debe tener uno o mas tipos.")
    private List<Long> tipos;
    @NotEmpty(message = "El producto debe tener uno o mas modelos.")
    private List<Long> modelos;

}
