package com.tallerinyecmotor.backend.service;

import com.tallerinyecmotor.backend.dto.DTOMarca;
import com.tallerinyecmotor.backend.dto.RespuestaService;
import com.tallerinyecmotor.backend.model.Marca;
import com.tallerinyecmotor.backend.model.Proveedor;
import com.tallerinyecmotor.backend.repository.IMarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaService implements IMarcaService {

    @Autowired
    private IMarcaRepository marcaRepo;

    @Override
    public List<Marca> getMarcas() {
        List<Marca> listaMarcas = marcaRepo.findAll();

        return listaMarcas;
    }

    @Override
    public RespuestaService saveMarcaCreate(DTOMarca marcaDTO) {

        RespuestaService resOK = new RespuestaService(true,"La Marca se creó correctamente","");
        try {
            Marca marcaFound = marcaRepo.findById(marcaDTO.getId()).orElse(null);

            if (marcaFound == null) {

                Marca marca = new Marca();
                marca.setNombre(marcaDTO.getNombre());

                marcaRepo.save(marca);

                return resOK;
            }else {
                RespuestaService resFound = new RespuestaService(false,"La marca ya existe con ese id","");
                return resFound;
            }

        }catch (Exception e){
            RespuestaService resFail = new RespuestaService(false,"La marca no se creó",e.getMessage());
            return resFail;
        }

    }

    @Override
    public void saveMarcaUpdate(Marca marca) {
        marcaRepo.save(marca);
    }

    @Override
    public RespuestaService deleteMarca(Long id) {

        RespuestaService resOK = new RespuestaService(true,"La marca se eliminó correctamente","");

        try {
            marcaRepo.deleteById(id);
            return resOK;

        }catch (Exception e){
            RespuestaService resFail = new RespuestaService(false,"La marca no se eliminó, por favor intente nuevamente",e.getMessage());
            return resFail;

        }

    }

    @Override
    public Marca findMarca(Long id) {

        Marca marcaFound = marcaRepo.findById(id).orElse(null);

        return marcaFound;
    }

    @Override
    public void updateMarca(Long id,String newNombre) {

        //con el this accedo a los metodos de la clase
        Marca marca = this.findMarca(id);
        marca.setNombre(newNombre);

        this.saveMarcaUpdate(marca);

    }

}
