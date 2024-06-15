package com.tallerinyecmotor.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;


@Getter
@Setter
public class DTOTipoProducto {

        @NotNull(message = "El id no puede ser nula")
        @Positive(message = "El id debe ser número positivo")
        private Long id;
        @NotNull(message = "El nombre no puede ser nulo")
        @Size(min = 1, max = 255,message = "El nombre debe tener entre 1 y 255 caracteres")
        private String nombre;

}
