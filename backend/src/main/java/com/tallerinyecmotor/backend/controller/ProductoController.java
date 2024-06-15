package com.tallerinyecmotor.backend.controller;

import com.tallerinyecmotor.backend.dto.DTOActualizacionStock;
import com.tallerinyecmotor.backend.dto.DTOProducto;
import com.tallerinyecmotor.backend.dto.DTOProductoEditar;
import com.tallerinyecmotor.backend.dto.RespuestaService;
import com.tallerinyecmotor.backend.model.Producto;
import com.tallerinyecmotor.backend.service.IProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductoController {

    @Autowired
    private IProductoService iProducto;

    @GetMapping("/producto/all")
    public ResponseEntity<?> getProducto(){

        try{

            List<Producto> productos = iProducto.getProducto();
            if (productos.isEmpty()){

                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay productos cargadas en la base de datos");

            }else {

                return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/producto/crear")
    public ResponseEntity<?> createProveedor(@Valid @RequestBody DTOProducto producto, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        try{

            RespuestaService res = iProducto.saveProducto(producto);

            if (res.isExito() == true){
                return ResponseEntity.status(HttpStatus.CREATED).body(res);
            }else{
                return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @DeleteMapping("/producto/eliminar/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable Long id){

        try{

            Producto productoFound = iProducto.findProductoById(id);

            if(productoFound==null){
                RespuestaService resNotFound = new RespuestaService(false,"El Producto no se encontró, vuelva a enviar un id de un producto valido","");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resNotFound);
            }else {
                RespuestaService res = iProducto.deleteProducto(id);
                if (res.isExito() == true){
                    return ResponseEntity.status(HttpStatus.OK).body(res);
                }else{
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
                }
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PatchMapping("/producto/editar")
    public ResponseEntity<?> updateProveedor(@Valid @RequestBody DTOProductoEditar producto, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        try{

            iProducto.updateProducto(producto);

            Producto productoUpdated = iProducto.findProductoById(producto.getId());

            return new ResponseEntity<Producto>(productoUpdated, HttpStatus.OK);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/producto/get-by-id/{id}")
    public ResponseEntity<?> getProveedorById(@PathVariable Long id){
        try{
            Producto productoFound = iProducto.findProductoById(id);

            if(productoFound==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El proveedor que se quiere encontrar no existe según el id enviado");
            }else {
                return new ResponseEntity<Producto>(productoFound ,HttpStatus.FOUND);
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/producto/get-by-tipo/{idTipo}")
    public ResponseEntity<?> getProductosByTipo(@PathVariable Long idTipo){
        try{
            List<Producto> productos = iProducto.getProductosByTipo(idTipo);

            if(productos==null){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("No tiene ningun producto o hubo un error");
            }else {
                return new ResponseEntity<List<Producto>>(productos ,HttpStatus.FOUND);
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/producto/get-by-modelo/{idModelo}")
    public ResponseEntity<?> getProductosByModelo(@PathVariable Long idModelo){
        try{
            List<Producto> productos = iProducto.getProductosByModelo(idModelo);

            if(productos==null){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("No tiene ningun producto o hubo un error");
            }else {
                return new ResponseEntity<List<Producto>>(productos ,HttpStatus.FOUND);
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }


    @GetMapping("/producto/get-by-marca/{idMarca}")
    public ResponseEntity<?> getProductosByMarca(@PathVariable Long idMarca){
        try{
            List<Producto> productos = iProducto.getProductosByMarca(idMarca);

            if(productos==null){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("No tiene ningun producto o hubo un error");
            }else {
                return new ResponseEntity<List<Producto>>(productos ,HttpStatus.FOUND);
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/producto/get-productos-a-reponer")
    public ResponseEntity<?> getProductoPorDebajoStockMinimo(){
        try{
            List<Producto> productos = iProducto.getProductoPorDebajoStockMinimo();

            if(productos!=null){
                return new ResponseEntity<List<Producto>>(productos ,HttpStatus.FOUND);
            }else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("hubo un error intente nuevamente");
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PatchMapping("/producto/descontar-stock")
    public ResponseEntity<?> getDescontarStock(@Valid @RequestBody DTOActualizacionStock actStock, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        try{
            if (actStock.getCantidad() > 0 && actStock.getId()!=null){

                RespuestaService res = iProducto.disminuirStock(actStock.getId(),actStock.getCantidad());

                if (res.isExito() == true){
                    return ResponseEntity.status(HttpStatus.OK).body(res);
                }else{
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
                }
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La peticion no tiene un formato correcto");
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PatchMapping("/producto/aumentar-stock")
    public ResponseEntity<?> getAumentarStock(@Valid @RequestBody DTOActualizacionStock actStock, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        try{
            if (actStock.getCantidad() > 0 && actStock.getId()!=null) {
                RespuestaService res = iProducto.aumentarStock(actStock.getId(), actStock.getCantidad());

                if (res.isExito() == true) {
                    return ResponseEntity.status(HttpStatus.OK).body(res);
                } else {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
                }
            }else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La peticion no tiene un formato correcto");
                }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

}
