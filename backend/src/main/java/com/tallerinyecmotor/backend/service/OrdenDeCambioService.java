package com.tallerinyecmotor.backend.service;

import com.tallerinyecmotor.backend.dto.DTOModeloCreate;
import com.tallerinyecmotor.backend.dto.DTOOrdenDeCambioCreate;
import com.tallerinyecmotor.backend.dto.DTOOrdenDeCambioEdit;
import com.tallerinyecmotor.backend.dto.RespuestaService;
import com.tallerinyecmotor.backend.model.*;
import com.tallerinyecmotor.backend.repository.IModeloRepository;
import com.tallerinyecmotor.backend.repository.IOrdenDeCambioRepository;
import com.tallerinyecmotor.backend.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdenDeCambioService implements IOrdenDeCambioService {

    @Autowired
    private IOrdenDeCambioRepository ordenRepository;

    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private IModeloRepository modeloRepository;

    @Autowired
    private IProductoService productoService;

    @Override
    public List<OrdenDeCambio> getOrdenes() {

        List<OrdenDeCambio> ordenesDeCambio = ordenRepository.findAll();

        return ordenesDeCambio;
    }

    @Override
    public RespuestaService saveOrdenCreate(DTOOrdenDeCambioCreate ordenDeCambioCreate) {

        RespuestaService resOK = new RespuestaService(true,"La orden de cambio se creó correctamente","");

        try {

            List<Long> idsProductos = ordenDeCambioCreate.getProductos();

            List<Producto> productos = new ArrayList<Producto>();

            //traerme todos los modelos
            if (!idsProductos.isEmpty()){
                for (Long idProducto : idsProductos) {
                    Producto oneProducto = productoRepository.findById(idProducto).orElse(null);

                    if (oneProducto == null){
                        RespuestaService resFound = new RespuestaService(false,"Todos o algúnos de los productos no existen por lo tanto no se puede crear la orden de cambio","");
                        return resFound;
                    }else{
                        productos.add(oneProducto);
                    }
                }
            }

            Modelo modeloFound = modeloRepository.findById(ordenDeCambioCreate.getModelo()).orElse(null);

            if (modeloFound == null){
                RespuestaService resNoModelo = new RespuestaService(false,"No existen el modelo por lo tanto no se puede crear la orden de cambio","");
                return resNoModelo;
            }else{
                OrdenDeCambio newOrdenDeCambio = new OrdenDeCambio();

                newOrdenDeCambio.setId(999999L);
                newOrdenDeCambio.setPatente(ordenDeCambioCreate.getPatente());
                newOrdenDeCambio.setOtros(ordenDeCambioCreate.getOtros());
                newOrdenDeCambio.setDescripcionOtros(ordenDeCambioCreate.getDescripcionOtros());
                newOrdenDeCambio.setManoDeObra(ordenDeCambioCreate.getManoDeObra());
                newOrdenDeCambio.setNombreCompleto(ordenDeCambioCreate.getNombreCompleto());
                newOrdenDeCambio.setModelo(modeloFound);
                newOrdenDeCambio.setProductos(productos);
                newOrdenDeCambio.calcularPrecioTotal();

                ordenRepository.save(newOrdenDeCambio);

                for (Producto producto: newOrdenDeCambio.getProductos()) {
                    productoService.disminuirStock(producto.getId(),1);
                }

                return resOK;
            }

        }catch (Exception e){
            RespuestaService resFail = new RespuestaService(false,"El producto no se creó, por favor intente nuevamente",e.getMessage());
            return resFail;

        }

    }

    @Override
    public void saveOrdenUpdate(OrdenDeCambio ordenDeCambio) {
        ordenRepository.save(ordenDeCambio);
    }

    @Override
    public RespuestaService deleteOrden(Long id) {

        RespuestaService resOK = new RespuestaService(true,"La orden de cambio se eliminó correctamente","");

        try {
            ordenRepository.deleteById(id);
            return resOK;

        }catch (Exception e){
            RespuestaService resFail = new RespuestaService(false,"La orden de cambio no se eliminó, por favor intente nuevamente",e.getMessage());
            return resFail;

        }
    }

    @Override
    public OrdenDeCambio findOrden(Long id) {
        OrdenDeCambio ordenDeCambio = ordenRepository.findById(id).orElse(null);
        return ordenDeCambio;
    }

    @Override
    public RespuestaService updateOrden(DTOOrdenDeCambioEdit ordenDeCambioEdit) {

        RespuestaService resOK = new RespuestaService(true,"La orden de cambio se actualizó correctamente","");

        try {
            OrdenDeCambio ordenDeCambioFound = ordenRepository.findById(ordenDeCambioEdit.getId()).orElse(null);
            if (ordenDeCambioFound == null){
                RespuestaService resFoundFail = new RespuestaService(false,"La orden de cambio no se encontro por lo tanto no se actualizó, por favor intente nuevamente","");
                return resFoundFail;
            }else {
                List<Producto> productos = new ArrayList<>();

                List<Long> idsProductos = ordenDeCambioEdit.getProductos();
                if (idsProductos.isEmpty()){
                    RespuestaService resNotFound = new RespuestaService(false,"Los productos no existen por lo tanto no se puede crear el producto","");
                    return resNotFound;
                }
                //traerme todos los modelos
                for (Long idProducto : idsProductos) {
                    Producto oneProducto = productoRepository.findById(idProducto).orElse(null);

                    if (oneProducto == null){
                        RespuestaService resNotFound = new RespuestaService(false,"Todos o algúnos de los productos no existen por lo tanto no se puede crear el producto","");
                        return resNotFound;
                    }else{
                        productos.add(oneProducto);
                    }
                }

                Modelo modelo = modeloRepository.findById(ordenDeCambioEdit.getModelo()).orElse(null);

                if (modelo == null){
                    RespuestaService resNotFound = new RespuestaService(false,"No existen el modelo buscado por lo tanto no se puede crear el producto","");
                    return resNotFound;
                }

                ordenDeCambioFound.setPatente(ordenDeCambioEdit.getPatente());
                ordenDeCambioFound.setNombreCompleto(ordenDeCambioEdit.getNombreCompleto());
                ordenDeCambioFound.setManoDeObra(ordenDeCambioEdit.getManoDeObra());
                ordenDeCambioFound.setOtros(ordenDeCambioEdit.getOtros());
                ordenDeCambioFound.setDescripcionOtros(ordenDeCambioEdit.getDescripcionOtros());
                ordenDeCambioFound.setModelo(modelo);
                ordenDeCambioFound.setProductos(productos);
                ordenDeCambioFound.calcularPrecioTotal();

                this.saveOrdenUpdate(ordenDeCambioFound);

                return resOK;
            }

        }catch (Exception e){
            RespuestaService resFail = new RespuestaService(false,"La orden de cambio no se eliminó, por favor intente nuevamente",e.getMessage());
            return resFail;

        }

    }

    @Override
    public List<OrdenDeCambio> findOrdenByPatente(String patente) {

        try {

            List<OrdenDeCambio> listaOrden = ordenRepository.findByPatente(patente);

            return listaOrden;

        }catch (Exception e){
            return null;
        }
    }
}
