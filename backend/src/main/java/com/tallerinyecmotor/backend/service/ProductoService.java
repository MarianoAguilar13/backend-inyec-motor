package com.tallerinyecmotor.backend.service;

import com.tallerinyecmotor.backend.dto.DTOProducto;
import com.tallerinyecmotor.backend.dto.DTOProductoEditar;
import com.tallerinyecmotor.backend.dto.RespuestaService;
import com.tallerinyecmotor.backend.model.*;
import com.tallerinyecmotor.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private IProductoRepository productoRepo;

    @Autowired
    private ITipoProductoRepository tipoRepo;

    @Autowired
    private IProveedorRepository proveedorRepo;

    @Autowired
    private IModeloRepository modeloRepo;


    @Override
    public List<Producto> getProducto() {

        List<Producto> listaProducto = productoRepo.findAll();

        return listaProducto;
    }

    @Override
    public RespuestaService saveProducto(DTOProducto productoDto) {

        RespuestaService resOK = new RespuestaService(true,"El Producto se creó correctamente","");

        try {

            List<Modelo> modelos =  modeloRepo.findAllById(productoDto.getModelos());

            List<Proveedor> proveedores = proveedorRepo.findAllById(productoDto.getProveedores());

            List<TipoProducto> tipos = tipoRepo.findAllById(productoDto.getTipos());

            Producto productoFound = productoRepo.findById(productoDto.getId()).orElse(null);

            if (productoFound == null) {

                Producto producto = new Producto();
                producto.setId(productoDto.getId());
                producto.setNombre(productoDto.getNombre());
                producto.setCodigo(productoDto.getCodigo());
                producto.setStockMin(productoDto.getStockMin());
                producto.setStockMax(productoDto.getStockMax());
                producto.setStockActual(productoDto.getStockActual());
                producto.setPrecioCosto(productoDto.getPrecioCosto());
                producto.setPrecioVenta(productoDto.getPrecioVenta());
                producto.setModelos(modelos);
                producto.setProveedores(proveedores);
                producto.setTipos(tipos);

                productoRepo.save(producto);

                return resOK;
            }
                else {
                    RespuestaService resFound = new RespuestaService(false,"El producto ya existe con ese id","");
                    return resFound;
                }

        }catch (Exception e){
            RespuestaService resFail = new RespuestaService(false,"El producto no se creó, por favor intente nuevamente",e.getMessage());
            return resFail;

        }
    }

    @Override
    public void saveProductoUpdate(Producto producto) {
        productoRepo.save(producto);
    }

    @Override
    public RespuestaService deleteProducto(Long id) {
        RespuestaService resOK = new RespuestaService(true,"El Producto se eliminó correctamente","");

        try {
            productoRepo.deleteById(id);
            return resOK;

        }catch (Exception e){
            RespuestaService resFail = new RespuestaService(false,"El producto no se eliminó correctamente, por favor intente nuevamente",e.getMessage());
            return resFail;

        }

    }

    @Override
    public Producto findProductoById(Long id) {
        Producto productoFound = productoRepo.findById(id).orElse(null);
        return productoFound;
    }

    @Override
    public List<Producto> getProductosByTipo(Long idTipo) {

        try {
            TipoProducto tipo = tipoRepo.findById(idTipo).orElse(null);

            if (tipo !=null){

                return tipo.getProductos();

            }else {
                return null;
            }

        }catch (Exception e){
            return  null;
        }

    }

    @Override
    public List<Producto> getProductosByModelo(Long idModelo) {
        try {
            Modelo modelo = modeloRepo.findById(idModelo).orElse(null);

            if (modelo !=null){

                return modelo.getProductos();

            }else {
                return null;
            }

        }catch (Exception e){
            return  null;
        }
    }


    @Override
    public List<Producto> getProductoPorDebajoStockMinimo() {

        try {

            List<Producto> productos = new ArrayList<>();

            List<Producto> allPrductos = this.getProducto();

            allPrductos.forEach((producto) -> {

                int stockActual = producto.getStockActual();
                int stockMin = producto.getStockMin();

                if (stockActual<stockMin){
                    productos.add(producto);
                }
            });

            return productos;

        }catch (Exception e){
            return null;
        }
    }

    @Override
    public RespuestaService disminuirStock(Long id, int restar) {
        RespuestaService resOK = new RespuestaService(true,"El Producto se actualizo correctamente","");
        RespuestaService resNumeroGrande = new RespuestaService(false,"El Producto no se actualizo correctamente, " +
                "debido a que quedará el estoc actual en negativo","");
        RespuestaService resNotFound = new RespuestaService(false,"El Producto que se quiere actualizar no se encuentra","");
        try {

            Producto productoFound = this.findProductoById(id);
            if (productoFound.getId()!=null){

                if (productoFound.getStockActual() < restar){
                    return  resNumeroGrande;
                }else{
                    int nuevoStockActual = productoFound.getStockActual() - restar;
                    productoFound.setStockActual(nuevoStockActual);
                    this.saveProductoUpdate(productoFound);
                    return resOK;
                }
            }else {
                return resNotFound;
            }

        }catch (Exception e){
            RespuestaService resCatch = new RespuestaService(false,"Ocurrió un problema en la actualización y la misma no se realizó",e.getMessage());
            return resCatch;
        }

    }

    @Override
    public RespuestaService aumentarStock(Long id, int sumar) {

        RespuestaService resOK = new RespuestaService(true,"El Producto se actualizo correctamente","");
        RespuestaService resNumeroGrande = new RespuestaService(false,"El Producto no se actualizo correctamente, " +
                "debido a que quedará se pasara del stock máximo","");
        RespuestaService resNotFound = new RespuestaService(false,"El Producto que se quiere actualizar no se encuentra","");
        try {

            Producto productoFound = this.findProductoById(id);
            if (productoFound.getId()!=null){

                int nuevoStockActual = productoFound.getStockActual() + sumar;

                if (nuevoStockActual > productoFound.getStockMax()){
                    return  resNumeroGrande;
                }else{
                    productoFound.setStockActual(nuevoStockActual);
                    this.saveProductoUpdate(productoFound);
                    return resOK;
                }
            }else {
                return resNotFound;
            }

        }catch (Exception e){
            RespuestaService resCatch = new RespuestaService(false,"Ocurrió un problema en la actualización y la misma no se realizó",e.getMessage());
            return resCatch;
        }

    }


    @Override
    public void updateProducto(DTOProductoEditar productoDTO) {

        Producto producto = this.findProductoById(productoDTO.getId());

        if (productoDTO.getNombre() != null){
            producto.setNombre(productoDTO.getNombre() );
        }

        if (productoDTO.getCodigo() != null){
            producto.setCodigo(productoDTO.getCodigo());
        }

        if (productoDTO.getStockMin() > 0){
            producto.setStockMin(productoDTO.getStockMin());
        }

        if (productoDTO.getStockMax() > 0){
            producto.setStockMax(productoDTO.getStockMax());
        }

        if (productoDTO.getStockActual() > 0){
            producto.setStockActual(productoDTO.getStockActual());
        }

        if (productoDTO.getPrecioVenta() > 0){
            producto.setPrecioVenta(productoDTO.getPrecioVenta());
        }

        if (productoDTO.getPrecioCosto() > 0){
            producto.setPrecioCosto(productoDTO.getPrecioCosto());
        }

        this.saveProductoUpdate(producto);
    }

}
