package com.tallerinyecmotor.backend.controller;

import com.tallerinyecmotor.backend.dto.DTOModeloCreate;
import com.tallerinyecmotor.backend.dto.RespuestaService;
import com.tallerinyecmotor.backend.model.Modelo;
import com.tallerinyecmotor.backend.service.IModeloService;
import com.tallerinyecmotor.backend.utils.SHA256;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ModeloController {

    @Autowired
    private IModeloService iModelo;

    private SHA256 controlPass = new SHA256();


    @GetMapping("/modelo/all")
    public ResponseEntity<?> getModelos(@RequestHeader("Authorization") String authorizationHeader){

        try{
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String pass = authorizationHeader.substring(7);

                if(controlPass.verifyPassword(pass)){

                    List<Modelo> modelos = iModelo.getModelos();
                    if (modelos.isEmpty()){

                        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay modelos cargadas en la base de datos");

                    }else {

                        return new ResponseEntity<List<Modelo>>(modelos, HttpStatus.OK);
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

    @PostMapping("/modelo/crear")
    public ResponseEntity<?> createModelo(@Valid @RequestBody DTOModeloCreate modeloDto, BindingResult bindingResult, @RequestHeader("Authorization")  String authorizationHeader){

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        try{
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String pass = authorizationHeader.substring(7);

                if(controlPass.verifyPassword(pass)){


                RespuestaService res = iModelo.saveModeloCreate(modeloDto);

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

    @DeleteMapping("/modelo/eliminar/{id}")
    public ResponseEntity<?> deleteModelo(@PathVariable Long id,@RequestHeader("Authorization") String authorizationHeader){

        try{
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String pass = authorizationHeader.substring(7);

                if(controlPass.verifyPassword(pass)){


                    Modelo modeloFound = iModelo.findModelo(id);

                    if(modeloFound==null){
                        RespuestaService resNotFound = new RespuestaService(false,"El modelo que se quiere eliminar no existe según el id enviado","");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resNotFound);
                    }else {
                        RespuestaService res = iModelo.deleteModelo(id);
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

    @PatchMapping("/modelo/editar")
    public ResponseEntity<?> updateModelo(@RequestBody DTOModeloCreate modelo,@RequestHeader("Authorization") String authorizationHeader){

        try{
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String pass = authorizationHeader.substring(7);

                if(controlPass.verifyPassword(pass)){

                    RespuestaService res = iModelo.updateModelo(modelo.getId(),modelo.getNombre(),modelo.getMotorLitros(), modelo.getMotorTipo(),modelo.getAnio());

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

    @GetMapping("/modelo/get-by-id/{id}")
    public ResponseEntity<?> getModeloById(@PathVariable Long id,@RequestHeader("Authorization") String authorizationHeader){


        try{
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String pass = authorizationHeader.substring(7);

                if(controlPass.verifyPassword(pass)){

                    Modelo modeloFound = iModelo.findModelo(id);

                    if(modeloFound==null){
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El modelo que se quiere encontrar no existe según el id enviado");
                    }else {
                        return new ResponseEntity<Modelo>(modeloFound ,HttpStatus.FOUND);
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


    @GetMapping("/modelo/get-by-name")
    public ResponseEntity<?> getModeloByName( @RequestParam(required = true) String nombre,@RequestHeader("Authorization") String authorizationHeader){


        try{
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String pass = authorizationHeader.substring(7);

                if(controlPass.verifyPassword(pass)){

                    System.out.println("Nombre: " + nombre);

                    if (nombre.length() > 1){
                    List<DTOModeloCreate> modeloFound = iModelo.findModeloByName(nombre);

                    if(modeloFound==null){
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El modeloque se quiere encontrar no existe según el id enviado");
                    }else {
                        return new ResponseEntity<List<DTOModeloCreate>>(modeloFound ,HttpStatus.FOUND);
                    }
                    }else{
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre del modelo debe tener al menos 2 caracteres");
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
