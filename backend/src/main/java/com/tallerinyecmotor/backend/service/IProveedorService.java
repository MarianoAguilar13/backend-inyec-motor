package com.tallerinyecmotor.backend.service;

import com.tallerinyecmotor.backend.dto.DTOProveedor;
import com.tallerinyecmotor.backend.dto.DTOTipoProducto;
import com.tallerinyecmotor.backend.dto.RespuestaService;
import com.tallerinyecmotor.backend.model.Proveedor;
import com.tallerinyecmotor.backend.model.TipoProducto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.List;

public interface IProveedorService {

    public List<Proveedor> getProveedor();

    public RespuestaService saveProveedor (DTOProveedor proveedorDto);

    public void saveProveedorUpdate (Proveedor proveedor);

    public RespuestaService deleteProveedor (Long id);

    public Proveedor findProveedor (Long id);

    public void updateProveedor (Long id,String cuit, String tel, String direccion, String nombre, String email);

}
