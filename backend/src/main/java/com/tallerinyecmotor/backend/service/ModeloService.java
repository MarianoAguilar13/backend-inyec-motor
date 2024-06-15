package com.tallerinyecmotor.backend.service;

import com.tallerinyecmotor.backend.dto.DTOModeloCreate;
import com.tallerinyecmotor.backend.dto.RespuestaService;
import com.tallerinyecmotor.backend.model.Marca;
import com.tallerinyecmotor.backend.model.Modelo;
import com.tallerinyecmotor.backend.model.Proveedor;
import com.tallerinyecmotor.backend.repository.IMarcaRepository;
import com.tallerinyecmotor.backend.repository.IModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeloService implements IModeloService{

    @Autowired
    private IModeloRepository modeloRepo;

    @Autowired
    private IMarcaRepository marcaRepo;

    @Override
    public List<Modelo> getModelos() {
        List<Modelo> listaModelos = modeloRepo.findAll();

        return listaModelos;
    }

    @Override
    public RespuestaService saveModeloCreate(DTOModeloCreate modeloDTO) {


            RespuestaService resOK = new RespuestaService(true,"El modelo se creó correctamente","");

            try {
                Modelo modeloFound = modeloRepo.findById(modeloDTO.getId()).orElse(null);
                Marca marcaFound = marcaRepo.findById(modeloDTO.getMarca()).orElse(null);

                if (modeloFound == null) {
                    if (marcaFound != null){

                        Modelo modelo = new Modelo();
                        modelo.setId(modeloDTO.getId());
                        modelo.setNombre(modeloDTO.getNombre());
                        modelo.setMotorLitros(modeloDTO.getMotorLitros());
                        modelo.setMotorTipo(modeloDTO.getMotorTipo());
                        modelo.setAnio(modeloDTO.getAnio());
                        modelo.setMarca(marcaFound);

                        modeloRepo.save(modelo);

                        return resOK;
                }else {
                        RespuestaService resNotMarca = new RespuestaService(false,"La marca no existe existe con ese id","");
                        return resNotMarca;
                    }

            }else {
                    RespuestaService resFound = new RespuestaService(false,"El modelo ya existe con ese id","");
                    return resFound;
                }

            }catch (Exception e){
                RespuestaService resFail = new RespuestaService(false,"El Proveedor no se creó",e.getMessage());
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
    public void updateModelo(Long id, String nombre, double motorLitros, String motorTipo, int anio) {

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

        this.saveModeloUpdate(modelo);

    }
}
