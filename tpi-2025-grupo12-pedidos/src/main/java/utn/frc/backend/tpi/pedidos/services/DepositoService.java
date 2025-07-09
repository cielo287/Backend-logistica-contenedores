package utn.frc.backend.tpi.pedidos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utn.frc.backend.tpi.pedidos.models.Deposito;
import utn.frc.backend.tpi.pedidos.repositories.DepositoRepository;

@Service
public class DepositoService {
    
    @Autowired
    private DepositoRepository depositoRepo;

    public List<Deposito> obtenerTodos(){
        return depositoRepo.findAll();
    }

    public Deposito obtenerPorId(Long id){
        return depositoRepo.findById(id).orElse(null);
    }

    public Deposito crear(Deposito deposito){
        return depositoRepo.save(deposito);
    }

    public Deposito actualizar(Long id, Deposito deposito){
        deposito.setId(id);
        return depositoRepo.save(deposito);
    }

    public void eliminar(Long id){
        depositoRepo.deleteById(id);
    }
}
