package utn.frc.backend.tpi.pedidos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utn.frc.backend.tpi.pedidos.models.Ciudad;
import utn.frc.backend.tpi.pedidos.models.Deposito;
import utn.frc.backend.tpi.pedidos.repositories.CiudadRepository;
import utn.frc.backend.tpi.pedidos.repositories.DepositoRepository;

@Service
public class DepositoService {
    
    @Autowired
    private DepositoRepository depositoRepo;

    @Autowired
    private CiudadRepository ciudadRepo;

    public List<Deposito> obtenerTodos(){
        return depositoRepo.findAll();
    }

    public Deposito obtenerPorId(Long id){
        return depositoRepo.findById(id).orElse(null);
    }


    public Deposito crear(Deposito deposito) {
    Long ciudadId = deposito.getCiudad() != null ? deposito.getCiudad().getId() : null;

    if (ciudadId == null) {
        throw new RuntimeException("La ciudad es obligatoria");
    }
    Ciudad ciudad = ciudadRepo.findById(ciudadId)
            .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

    deposito.setCiudad(ciudad);

    return depositoRepo.save(deposito);

    }


    public Deposito actualizar(Long id, Deposito deposito) {
    deposito.setId(id);
    if (deposito.getCiudad() != null && deposito.getCiudad().getId() != null) {
        Ciudad ciudad = ciudadRepo.findById(deposito.getCiudad().getId())
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
        deposito.setCiudad(ciudad);
    }
    return depositoRepo.save(deposito);
    }


    public void eliminar(Long id){
        depositoRepo.deleteById(id);
    }
}
