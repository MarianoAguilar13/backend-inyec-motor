package com.tallerinyecmotor.backend.controller;

import com.tallerinyecmotor.backend.dto.DTOMarca;
import com.tallerinyecmotor.backend.dto.RespuestaService;
import com.tallerinyecmotor.backend.model.Marca;
import com.tallerinyecmotor.backend.service.IMarcaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MarcaController {

    @Autowired
    private IMarcaService iMarca;

    @GetMapping("/marca/all")
    public ResponseEntity<?> getMarcas(){

        try{

            List<Marca> marcas = iMarca.getMarcas();
            if (marcas.isEmpty()){

                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay marcas cargadas en la base de datos");

            }else {

                return new ResponseEntity<List<Marca>>(marcas, HttpStatus.OK);
            }



        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/marca/crear")
    public ResponseEntity<?> createMarca(@Valid @RequestBody DTOMarca marca, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        try{

            RespuestaService res = iMarca.saveMarcaCreate(marca);

            if (res.isExito() == true){
                return ResponseEntity.status(HttpStatus.CREATED).body(res);
            }else{
                return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @DeleteMapping("/marca/eliminar/{id}")
    public ResponseEntity<?> deleteMarca(@PathVariable Long id){

        try{
            Marca marcaFound = iMarca.findMarca(id);

            if(marcaFound==null){
                RespuestaService resNotFound = new RespuestaService(false,"La Marca que se quiere eliminar no existe según el id enviado","");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resNotFound);
            }else {
                RespuestaService res = iMarca.deleteMarca(id);
                if (res.isExito() == true) {
                    return ResponseEntity.status(HttpStatus.OK).body(res);
                } else {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
                }
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PatchMapping("/marca/editar")
    public ResponseEntity<?> updateMarca(@RequestBody DTOMarca marca){

        try{

            iMarca.updateMarca(marca.getId(),marca.getNombre());

            Marca marcaUpdated = iMarca.findMarca(marca.getId());

            return new ResponseEntity<Marca>(marcaUpdated, HttpStatus.OK);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/marca/get-by-id/{id}")
    public ResponseEntity<?> getMarcaById(@PathVariable Long id){


        try{

            Marca marcaFound = iMarca.findMarca(id);

            if(marcaFound==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La marca que se quiere encontrar no existe según el id enviado");
            }else {
                return new ResponseEntity<Marca>(marcaFound ,HttpStatus.FOUND);
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }


}
