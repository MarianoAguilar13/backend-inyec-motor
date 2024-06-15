package com.tallerinyecmotor.backend.service;


import com.tallerinyecmotor.backend.dto.DTOTipoProducto;
import com.tallerinyecmotor.backend.dto.RespuestaService;
import com.tallerinyecmotor.backend.model.TipoProducto;

import java.util.List;

public interface ITipoProductoService {

    public List<TipoProducto> getTipo();

    public RespuestaService saveTipoCreate (DTOTipoProducto tipoDTO);

    public void saveTipoUpdate (TipoProducto tipo);

    public RespuestaService deleteTipo (Long id);

    public TipoProducto findTipo (Long id);

    public void updateTipo (Long id, String nombre);
}
