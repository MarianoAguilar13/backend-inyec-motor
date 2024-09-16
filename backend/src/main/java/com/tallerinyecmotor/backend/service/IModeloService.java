package com.tallerinyecmotor.backend.service;

import com.tallerinyecmotor.backend.dto.DTOModeloCreate;
import com.tallerinyecmotor.backend.dto.RespuestaService;
import com.tallerinyecmotor.backend.model.Modelo;

import java.util.List;

public interface IModeloService {


    public List<Modelo> getModelos();

    public RespuestaService saveModeloCreate (DTOModeloCreate modeloDTO);

    public void saveModeloUpdate (Modelo modelo);

    public RespuestaService deleteModelo (Long id);

    public Modelo findModelo (Long id);

    public RespuestaService updateModelo (Long id, String nombre, double motorLitros, String motorTipo, int anio);

    public List<DTOModeloCreate> findModeloByName (String nombre);

}
