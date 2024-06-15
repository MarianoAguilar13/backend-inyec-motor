package com.tallerinyecmotor.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class DTOProveedor {

    @NotNull(message = "El id no puede ser nula")
    @Positive(message = "El id debe ser número positivo")
    private Long id;
    @NotNull(message = "El cuit no puede ser nulo")
    @Size(min = 8, max = 30,message = "El cuit debe tener entre 8 y 30 caracteres")
    private String cuit;
    @NotNull(message = "El telefono no puede ser nulo")
    @Size(min = 8, max = 25,message = "El telefono debe tener entre 8 y 25 caracteres")
    private String tel;
    @NotNull(message = "La dirección no puede ser nulo")
    @Size(min = 10, max = 255,message = "La dirección debe tener entre 10 y 255 caracteres")
    private String direccion;
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 3, max = 255,message = "El nombre debe tener entre 3 y 255 caracteres")
    private String nombre;
    @NotNull(message = "El email no puede ser nulo")
    @Email(message = "Debe ser un correo electrónico válido")
    private String email;
}
