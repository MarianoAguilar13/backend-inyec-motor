package com.tallerinyecmotor.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class DTOMarca {
    @NotNull(message = "El id no puede ser nula")
    @Positive(message = "El id debe ser número positivo")
    private Long id;
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 3, max = 50,message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;
}
