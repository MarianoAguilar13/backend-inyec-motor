package com.tallerinyecmotor.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RespuestaService {

    private boolean exito;
    private String mensaje;
    private String errorMensaje;

    public RespuestaService(boolean exito, String mensaje,String errorMensaje) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.errorMensaje = errorMensaje;
    }
}
