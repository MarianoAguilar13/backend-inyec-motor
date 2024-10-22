package com.tallerinyecmotor.backend.service;

import com.tallerinyecmotor.backend.dto.DTOModeloCreate;
import com.tallerinyecmotor.backend.dto.DTOOrdenDeCambioCreate;
import com.tallerinyecmotor.backend.dto.DTOOrdenDeCambioEdit;
import com.tallerinyecmotor.backend.dto.RespuestaService;
import com.tallerinyecmotor.backend.model.Modelo;
import com.tallerinyecmotor.backend.model.OrdenDeCambio;

import java.util.List;

public interface IOrdenDeCambioService {

    public List<OrdenDeCambio> getOrdenes();

    public RespuestaService saveOrdenCreate (DTOOrdenDeCambioCreate ordenDeCambioCreate);

    public void saveOrdenUpdate (OrdenDeCambio ordenDeCambio);

    public RespuestaService deleteOrden (Long id);

    public OrdenDeCambio findOrden (Long id);

    public RespuestaService updateOrden (DTOOrdenDeCambioEdit ordenDeCambioEdit);

    public List<OrdenDeCambio> findOrdenByPatente (String patente);

}
