package com.tallerinyecmotor.backend.dto;



import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DTOActualizacionStock {
    @NotNull(message = "El id no puede ser nula")
    @Positive(message = "El id debe ser número positivo")
    private Long id;
    @NotNull(message = "La cantidad no puede ser nula")
    @Positive(message = "La cantidad debe ser un número positivo")
    private int cantidad;
}
