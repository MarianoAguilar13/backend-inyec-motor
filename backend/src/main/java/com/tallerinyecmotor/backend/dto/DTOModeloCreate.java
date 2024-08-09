package com.tallerinyecmotor.backend.dto;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter @Setter
public class DTOModeloCreate {
    @NotNull(message = "El id no puede ser nula")
    @Positive(message = "El id debe ser número positivo")
    private Long id;
    @NotNull(message = "El nombre no puede ser nula")
    @Size(min = 1, max = 255,message = "El nombre debe tener entre 1 y 255 caracteres")
    private String nombre;
    @DecimalMin(value = "1.0", message = "La capacidad en litros del motor ser mayor o igual a 1.0")
    @DecimalMax(value = "5.0", message = "La capacidad en litros del motor ser menor o igual a 5.0")
    private double motorLitros;
    @NotNull(message = "El nombre no puede ser nula")
    @Size(min = 3, max = 50,message = "El nombre debe tener entre 3 y 50 caracteres")
    private String motorTipo;
    @Max(value = 2040, message = "El año debe ser menor o igual a 2040")
    @Min(value = 1950, message = "El año debe ser mayor o igual a 1950")
    private int anio;
    @NotNull(message = "La marca no puede ser nula")
    @Size(min = 3, max = 50,message = "La marca debe tener entre 3 y 50 caracteres")
    private String marca;


}
