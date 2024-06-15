package com.tallerinyecmotor.backend.service;

import com.tallerinyecmotor.backend.dto.DTOMarca;
import com.tallerinyecmotor.backend.dto.RespuestaService;
import com.tallerinyecmotor.backend.model.Marca;

import java.util.List;

public interface IMarcaService {

    public List<Marca> getMarcas();

    public RespuestaService saveMarcaCreate (DTOMarca marcaDTO);

    public void saveMarcaUpdate (Marca marca);

    public RespuestaService deleteMarca (Long id);

    public Marca findMarca (Long id);

    public void updateMarca (Long id, String newNombre);

}
