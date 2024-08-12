package com.tallerinyecmotor.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class DTOAuth {
    @NotNull(message = "La contraseña no puede ser nula")
    @Size(min = 1,max = 100)
    private String password;
}
