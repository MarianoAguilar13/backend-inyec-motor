package com.tallerinyecmotor.backend.service;

import com.tallerinyecmotor.backend.dto.DTOProveedor;
import com.tallerinyecmotor.backend.dto.RespuestaService;
import com.tallerinyecmotor.backend.model.Proveedor;
import com.tallerinyecmotor.backend.repository.IProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService implements IProveedorService{

    @Autowired
    private IProveedorRepository proveedorRepo;

    @Override
    public List<Proveedor> getProveedor() {
        List<Proveedor> listaProveedor = proveedorRepo.findAll();

        return listaProveedor;
    }

    @Override
    public RespuestaService saveProveedor(DTOProveedor proveedorDTO) {

        RespuestaService resOK = new RespuestaService(true,"El Proveedor se creó correctamente","");
        try {
            Proveedor proveedorFound = proveedorRepo.findById(proveedorDTO.getId()).orElse(null);

            if (proveedorFound == null) {

                Proveedor proveedor = new Proveedor();
                proveedor.setId(proveedorDTO.getId());
                proveedor.setNombre(proveedorDTO.getNombre());
                proveedor.setCuit(proveedorDTO.getCuit());
                proveedor.setDireccion(proveedorDTO.getDireccion());
                proveedor.setEmail(proveedorDTO.getEmail());
                proveedor.setTel(proveedorDTO.getTel());

                proveedorRepo.save(proveedor);

                return resOK;
            }else {
                RespuestaService resFound = new RespuestaService(false,"El proveedor ya existe con ese id","");
                return resFound;
            }

        }catch (Exception e){
            RespuestaService resFail = new RespuestaService(false,"El Proveedor no se creó",e.getMessage());
            return resFail;
        }
    }

    @Override
    public void saveProveedorUpdate(Proveedor proveedor) {
        proveedorRepo.save(proveedor);
    }

    @Override
    public RespuestaService deleteProveedor(Long id) {

        RespuestaService resOK = new RespuestaService(true,"El Proveedor se eliminó correctamente","");

        try {
            proveedorRepo.deleteById(id);
            return resOK;

        }catch (Exception e){
            RespuestaService resFail = new RespuestaService(false,"El proveedor no se eliminó, por favor intente nuevamente",e.getMessage());
            return resFail;

        }
    }

    @Override
    public Proveedor findProveedor(Long id) {
        Proveedor proveedorFound = proveedorRepo.findById(id).orElse(null);
        return proveedorFound;
    }

    @Override
    public void updateProveedor(Long id, String cuit, String tel, String direccion, String nombre, String email) {

        Proveedor proveedor = this.findProveedor(id);

        if (nombre != null){
            proveedor.setNombre(nombre);
        }

        if (cuit != null){
            proveedor.setCuit(cuit);
        }

        if (tel != null){
            proveedor.setTel(tel);
        }

        if (direccion != null){
            proveedor.setDireccion(direccion);
        }

        if (email != null){
            proveedor.setEmail(email);
        }

        this.saveProveedorUpdate(proveedor);
    }
}
