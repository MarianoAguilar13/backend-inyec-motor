package com.tallerinyecmotor.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class DTOProductoEditar {

    @NotNull(message = "El id no puede ser nula")
    @Positive(message = "El id debe ser número positivo")
    private Long id;
    private String codigo;
    private String nombre;
    private int stockMin;
    private int stockMax;
    private int stockActual;
    private double precioVenta;
    private double precioCosto;
}
