package com.tallerinyecmotor.backend.controller;

import com.tallerinyecmotor.backend.dto.DTOModeloCreate;
import com.tallerinyecmotor.backend.dto.DTOOrdenDeCambioCreate;
import com.tallerinyecmotor.backend.dto.DTOOrdenDeCambioEdit;
import com.tallerinyecmotor.backend.dto.RespuestaService;
import com.tallerinyecmotor.backend.model.Modelo;
import com.tallerinyecmotor.backend.model.OrdenDeCambio;
import com.tallerinyecmotor.backend.service.IModeloService;
import com.tallerinyecmotor.backend.service.IOrdenDeCambioService;
import com.tallerinyecmotor.backend.utils.SHA256;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrdenDeCambioController {

    @Autowired
    private IOrdenDeCambioService iOrdenDeCambo;

    private SHA256 controlPass = new SHA256();


    @GetMapping("/orden/all")
    public ResponseEntity<?> getOrdenes(@RequestHeader("Authorization") String authorizationHeader){

        try{
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String pass = authorizationHeader.substring(7);

                if(controlPass.verifyPassword(pass)){

                    List<OrdenDeCambio> ordenesDeCambio = iOrdenDeCambo.getOrdenes();
                    if (ordenesDeCambio.isEmpty()){

                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay ordenes de cambio cargadas en la base de datos");

                    }else {

                        return new ResponseEntity<List<OrdenDeCambio>>(ordenesDeCambio, HttpStatus.OK);
                    }
                }else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("La contraseña ingresada no es la correcta, por favor intente nuevamente");
                }
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header no existe o invalido");
            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/orden/crear")
    public ResponseEntity<?> createOrden(@Valid @RequestBody DTOOrdenDeCambioCreate ordenDeCambioCreate, BindingResult bindingResult, @RequestHeader("Authorization")  String authorizationHeader){

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        try{
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String pass = authorizationHeader.substring(7);

                if(controlPass.verifyPassword(pass)){


                    RespuestaService res = iOrdenDeCambo.saveOrdenCreate(ordenDeCambioCreate);

                    if (res.isExito() == true){
                        return ResponseEntity.status(HttpStatus.CREATED).body(res);
                    }else{
                        return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
                    }
                }else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("La contraseña ingresada no es la correcta, por favor intente nuevamente");
                }
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header no existe o invalido");
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @DeleteMapping("/orden/eliminar/{id}")
    public ResponseEntity<?> deleteOrden(@PathVariable Long id,@RequestHeader("Authorization") String authorizationHeader){

        try{
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String pass = authorizationHeader.substring(7);

                if(controlPass.verifyPassword(pass)){


                    OrdenDeCambio ordenFound = iOrdenDeCambo.findOrden(id);

                    if(ordenFound==null){
                        RespuestaService resNotFound = new RespuestaService(false,"La orden que se quiere eliminar no existe según el id enviado","");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resNotFound);
                    }else {
                        RespuestaService res = iOrdenDeCambo.deleteOrden(id);
                        if (res.isExito() == true) {
                            return ResponseEntity.status(HttpStatus.OK).body(res);
                        } else {
                            return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
                        }
                    }
                }else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("La contraseña ingresada no es la correcta, por favor intente nuevamente");
                }
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header no existe o invalido");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PatchMapping("/orden/editar")
    public ResponseEntity<?> updateOrden(@RequestBody DTOOrdenDeCambioEdit ordenDeCambio, @RequestHeader("Authorization") String authorizationHeader){

        try{
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String pass = authorizationHeader.substring(7);

                if(controlPass.verifyPassword(pass)){

                    RespuestaService res = iOrdenDeCambo.updateOrden(ordenDeCambio);

                    if (res.isExito() == true){
                        return ResponseEntity.status(HttpStatus.CREATED).body(res);
                    }else{
                        return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
                    }
                }else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("La contraseña ingresada no es la correcta, por favor intente nuevamente");
                }
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header no existe o invalido");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/orden/get-by-id/{id}")
    public ResponseEntity<?> getOrdenById(@PathVariable Long id,@RequestHeader("Authorization") String authorizationHeader){


        try{
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String pass = authorizationHeader.substring(7);

                if(controlPass.verifyPassword(pass)){

                    OrdenDeCambio ordenDeCambioFound = iOrdenDeCambo.findOrden(id);

                    if(ordenDeCambioFound==null){
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La orden de cambio que se quiere encontrar no existe según el id enviado");
                    }else {
                        return new ResponseEntity<OrdenDeCambio>(ordenDeCambioFound ,HttpStatus.OK);
                    }
                }else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("La contraseña ingresada no es la correcta, por favor intente nuevamente");
                }
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header no existe o invalido");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @GetMapping("/orden/get-by-patente")
    public ResponseEntity<?> getOrdenByPatente( @RequestParam(required = true) String patente,@RequestHeader("Authorization") String authorizationHeader){


        try{
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String pass = authorizationHeader.substring(7);

                if(controlPass.verifyPassword(pass)){

                    if (patente.length() > 3 && patente.length() < 11){
                        List<OrdenDeCambio> ordenesDeCambio = iOrdenDeCambo.findOrdenByPatente(patente);

                        if(ordenesDeCambio==null){
                            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Las ordenes que se quiere encontrar no existe según el id enviado");
                        }else {
                            return new ResponseEntity<List<OrdenDeCambio>>(ordenesDeCambio ,HttpStatus.OK);
                        }
                    }else{
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La patente de la orden que desea buscar debe tener al menos 4 caracteres y maximo de 10");
                    }
                }else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("La contraseña ingresada no es la correcta, por favor intente nuevamente");
                }
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header no existe o invalido");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
