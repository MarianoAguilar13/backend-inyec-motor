package com.tallerinyecmotor.backend.service;

import com.tallerinyecmotor.backend.dto.DTOModeloCreate;
import com.tallerinyecmotor.backend.dto.RespuestaService;
import com.tallerinyecmotor.backend.model.Modelo;
import com.tallerinyecmotor.backend.model.Proveedor;
import com.tallerinyecmotor.backend.repository.IModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ModeloService implements IModeloService{

    @Autowired
    private IModeloRepository modeloRepo;

    @Override
    public List<Modelo> getModelos() {
        List<Modelo> listaModelos = modeloRepo.findAll();

        return listaModelos;
    }

    @Override
    public RespuestaService saveModeloCreate(DTOModeloCreate modeloDTO) {


            RespuestaService resOK = new RespuestaService(true,"El modelo se creó correctamente","");

            try {

                List<Modelo> modelosFound = modeloRepo.findByNombre(modeloDTO.getNombre());

                List<Modelo> listaModelos = new ArrayList<>();

                        for (Modelo modelo:modelosFound) {
                            if (modelo.getNombre().equals(modeloDTO.getNombre()) && modelo.getMotorTipo().equals(modeloDTO.getMotorTipo()) && modelo.getAnio() == modeloDTO.getAnio() && modelo.getMotorLitros() == modeloDTO.getMotorLitros()) {
                                listaModelos.add(modelo);
                            }
                        }

                        for (Modelo printModelo : listaModelos) {
                            System.out.println(printModelo.getNombre() + " " + printModelo.getMotorLitros() + " " + printModelo.getMotorTipo() + " " + printModelo.getAnio());
                        }


                        if (listaModelos.isEmpty()){
                            Modelo modelo = new Modelo();
                            modelo.setId(modeloDTO.getId());
                            modelo.setNombre(modeloDTO.getNombre());
                            modelo.setMotorLitros(modeloDTO.getMotorLitros());
                            modelo.setMotorTipo(modeloDTO.getMotorTipo());
                            modelo.setAnio(modeloDTO.getAnio());
                            modelo.setMarca(modeloDTO.getMarca());

                            modeloRepo.save(modelo);

                            return resOK;
                        }else {
                            RespuestaService resExist = new RespuestaService(false,"El modelo ya existe con un mismo nombre, tipo de motor, litros de motor y año","");
                            return resExist;
                    }
            }catch (Exception e){
                RespuestaService resFail = new RespuestaService(false,"El Modelo no se creó",e.getMessage());
                return resFail;
            }

    }

    @Override
    public void saveModeloUpdate(Modelo modelo) {



        modeloRepo.save(modelo);

    }

    @Override
    public RespuestaService deleteModelo(Long id) {

        RespuestaService resOK = new RespuestaService(true,"El modelo se eliminó correctamente","");

        try {
            modeloRepo.deleteById(id);
            return resOK;

        }catch (Exception e){
            RespuestaService resFail = new RespuestaService(false,"El modelo no se eliminó, por favor intente nuevamente",e.getMessage());
            return resFail;

        }

    }

    @Override
    public Modelo findModelo(Long id) {

        Modelo modeloFound = modeloRepo.findById(id).orElse(null);
        return modeloFound;
    }

    //la marca no se puede cambiar, si se confundio, eliminar el modelo y crearlo de nuevo
    @Override
    public RespuestaService updateModelo(Long id, String nombre, double motorLitros, String motorTipo, int anio) {

        RespuestaService resOK = new RespuestaService(true,"El modelo se actualizó correctamente","");

        try {

            Modelo modelo = this.findModelo(id);

            if (nombre != null){
                modelo.setNombre(nombre);
            }

            if (motorLitros > 0){
                modelo.setMotorLitros(motorLitros);
            }

            if (motorTipo != null){
                modelo.setMotorTipo(motorTipo);
            }

            if (anio > 1950){
                modelo.setAnio(anio);
            }

            List<Modelo> modelosFound = modeloRepo.findByNombre(modelo.getNombre());

            List<Modelo> listaModelos = new ArrayList<>();

                for (Modelo modeloFor:modelosFound) {
                    if (modeloFor.getNombre().equals(modelo.getNombre()) && modeloFor.getMotorTipo().equals(modelo.getMotorTipo()) && modeloFor.getAnio() == modelo.getAnio() && modeloFor.getMotorLitros() == modelo.getMotorLitros()) {
                        listaModelos.add(modelo);
                    }
                }

                if (listaModelos.isEmpty()){
                    this.saveModeloUpdate(modelo);

                    return resOK;

                }else {
                    RespuestaService resExist = new RespuestaService(false,"El modelo ya existe con un mismo nombre, tipo de motor, litros de motor y año","");

                    return resExist;
                }
        }catch (Exception e){
            RespuestaService resFail = new RespuestaService(false,"El Modelo no se creó",e.getMessage());

            return resFail;
        }

    }

    @Override
    public List<DTOModeloCreate> findModeloByName(String nombre){

        List<Modelo> listaModelos = modeloRepo.findByNombre(nombre);

        List<DTOModeloCreate> listaDTOModelos = new ArrayList<>();

        for (Modelo modelo:listaModelos) {
            listaDTOModelos.add(modelo.ModeloToDTOModeloCreate());
        }

        return listaDTOModelos;
    }
}
