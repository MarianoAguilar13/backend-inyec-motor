package com.tallerinyecmotor.backend.controller;

import com.tallerinyecmotor.backend.dto.DTOTipoProducto;
import com.tallerinyecmotor.backend.dto.RespuestaService;
import com.tallerinyecmotor.backend.model.TipoProducto;
import com.tallerinyecmotor.backend.service.ITipoProductoService;
import com.tallerinyecmotor.backend.utils.SHA256;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class TipoProductoController {

    @Autowired
    private ITipoProductoService iTipo;

    private SHA256 controlPass = new SHA256();

    @GetMapping("/tipo/all")
    public ResponseEntity<?> getTipos(@RequestHeader("Authorization") String authorizationHeader){

        try{
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String pass = authorizationHeader.substring(7);

                if(controlPass.verifyPassword(pass)){

                    List<TipoProducto> tipos = iTipo.getTipo();
                    if (tipos.isEmpty()){

                        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay tipos de producto cargadas en la base de datos");

                    }else {

                        return new ResponseEntity<List<TipoProducto>>(tipos, HttpStatus.OK);
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

    @PostMapping("/tipo/crear")
    public ResponseEntity<?> createTipo(@Valid @RequestBody DTOTipoProducto tipoDto, BindingResult bindingResult,@RequestHeader("Authorization") String authorizationHeader){

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        try{
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String pass = authorizationHeader.substring(7);

                if(controlPass.verifyPassword(pass)){

                    RespuestaService res = iTipo.saveTipoCreate(tipoDto);

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

    @DeleteMapping("/tipo/eliminar/{id}")
    public ResponseEntity<?> deleteModelo(@PathVariable Long id,@RequestHeader("Authorization") String authorizationHeader){

        try{
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String pass = authorizationHeader.substring(7);

                if(controlPass.verifyPassword(pass)){

                    TipoProducto tipoFound = iTipo.findTipo(id);

                    if(tipoFound==null){
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El tipo de producto que se quiere eliminar no existe según el id enviado");
                    }else {
                        iTipo.deleteTipo(id);
                        return ResponseEntity.status(HttpStatus.CREATED).body("El tipo de producto se eliminó correctamente");
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

    @PatchMapping("/tipo/editar")
    public ResponseEntity<?> updateTipo(@RequestBody DTOTipoProducto tipo,@RequestHeader("Authorization") String authorizationHeader){

        try{
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String pass = authorizationHeader.substring(7);

                if(controlPass.verifyPassword(pass)){

                    iTipo.updateTipo(tipo.getId(),tipo.getNombre());

                    TipoProducto tipoUpdated = iTipo.findTipo(tipo.getId());

                    return new ResponseEntity<TipoProducto>(tipoUpdated, HttpStatus.OK);
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

    @GetMapping("/tipo/get-by-id/{id}")
    public ResponseEntity<?> getTipoById(@PathVariable Long id,@RequestHeader("Authorization") String authorizationHeader){


        try{
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String pass = authorizationHeader.substring(7);

                if(controlPass.verifyPassword(pass)){

                    TipoProducto tipoFound = iTipo.findTipo(id);

                    if(tipoFound==null){
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El tipo de producto se quiere encontrar no existe según el id enviado");
                    }else {
                        return new ResponseEntity<TipoProducto>(tipoFound ,HttpStatus.FOUND);
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
