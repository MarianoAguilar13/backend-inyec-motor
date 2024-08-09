package com.tallerinyecmotor.backend.service;

import com.tallerinyecmotor.backend.dto.DTOTipoProducto;
import com.tallerinyecmotor.backend.dto.RespuestaService;
import com.tallerinyecmotor.backend.model.TipoProducto;
import com.tallerinyecmotor.backend.repository.ITipoProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoProductoService implements ITipoProductoService {

    @Autowired
    private ITipoProductoRepository tipoRepo;

    @Override
    public List<TipoProducto> getTipo() {

        List<TipoProducto> listaTipos = tipoRepo.findAll();

        return listaTipos;
    }

    @Override
    public RespuestaService saveTipoCreate(DTOTipoProducto tipoDTO) {

        RespuestaService resOK = new RespuestaService(true,"El tipo de producto se creó correctamente","");

        try {

            TipoProducto tipo = tipoRepo.findById(tipoDTO.getId()).orElse(null);

            if (tipo == null) {

                TipoProducto tipoProducto = new TipoProducto();
                tipoProducto.setId(tipoDTO.getId());
                tipoProducto.setNombre(tipoDTO.getNombre());

                tipoRepo.save(tipoProducto);

                return resOK;
            }else {
                RespuestaService resFound = new RespuestaService(false,"El tipo de producto ya existe con ese id","");
                return resFound;
            }

        }catch (Exception e){
            RespuestaService resFail = new RespuestaService(false,"El tipo de producto no se creó",e.getMessage());
            return resFail;
        }
    }

    @Override
    public void saveTipoUpdate(TipoProducto tipo) {
        tipoRepo.save(tipo);
    }

    @Override
    public RespuestaService deleteTipo(Long id) {

        RespuestaService resOK = new RespuestaService(true,"El tipo de producto se eliminó correctamente","");

        try {
            tipoRepo.deleteById(id);
            return resOK;

        }catch (Exception e){
            RespuestaService resFail = new RespuestaService(false,"El tipo de producto no se eliminó, por favor intente nuevamente",e.getMessage());
            return resFail;

        }
    }

    @Override
    public TipoProducto findTipo(Long id) {
        TipoProducto tipoFound = tipoRepo.findById(id).orElse(null);
        return tipoFound;
    }

    @Override
    public void updateTipo(Long id, String nombre) {
        TipoProducto tipo = this.findTipo(id);

        if (nombre != null){
            tipo.setNombre(nombre);
        }

        this.saveTipoUpdate(tipo);
    }
}
