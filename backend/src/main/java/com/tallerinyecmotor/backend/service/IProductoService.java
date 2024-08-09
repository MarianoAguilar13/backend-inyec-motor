package com.tallerinyecmotor.backend.service;

import com.tallerinyecmotor.backend.dto.DTOProducto;
import com.tallerinyecmotor.backend.dto.DTOProductoEditar;
import com.tallerinyecmotor.backend.dto.RespuestaService;
import com.tallerinyecmotor.backend.model.Producto;

import java.util.List;

public interface IProductoService {

    public List<Producto> getProducto();

    public RespuestaService saveProducto (DTOProducto productoDTO);

    public void saveProductoUpdate (Producto producto);

    public RespuestaService deleteProducto (Long id);

    public Producto findProductoById (Long id);

    public  List<Producto> getProductosByTipo(Long idTipo);

    public  List<Producto> getProductosByModelo(Long idModelo);

    public  List<Producto> getProductoPorDebajoStockMinimo();

    public RespuestaService disminuirStock(Long id, int restar);

    public RespuestaService aumentarStock(Long id, int sumar);

    public void updateProducto (DTOProductoEditar producto);

}
