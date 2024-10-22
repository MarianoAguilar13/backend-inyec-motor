package com.tallerinyecmotor.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
public class DTOOrdenDeCambioEdit {

    @NotNull(message = "El id no puede ser nulo")
    @Positive(message = "El id debe ser un número entero positivo")
    private Long id;
    @NotNull(message = "El Nombre completo no puede ser nulo")
    @Size(min = 6, max = 255,message = "El nombre completo debe tener entre 3 y 255 caracteres")
    private String nombreCompleto;
    @NotNull(message = "La patente no puede ser nula")
    @Size(min = 4, max = 8,message = "La patente debe tener entre 4 y 8 caracteres")
    private String patente;
    @NotNull(message = "La mano de obre no puede ser nulo")
    @PositiveOrZero(message = "La mano de obre debe ser un número positivo o 0.")
    private double manoDeObra;
    @PositiveOrZero(message = "La mano de obre debe ser un número positivo o 0.")
    private double otros;
    @Size(max = 255,message = "La descripción debe tener máximo 255 caracteres")
    private String descripcionOtros;
    private List<Long> productos;
    @NotNull(message = "La orden de cambio debe tener modelo")
    @Positive(message = "El id del modelo debe ser número positivo")
    private Long modelo;

}
