package utn.frc.backend.tpi.pedidos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import utn.frc.backend.tpi.pedidos.models.Camion;
import utn.frc.backend.tpi.pedidos.repositories.CamionRepository;

@Service
public class CamionService {
    
    @Autowired
    private CamionRepository camionRepo;

    public List<Camion> obtenerTodos(){
        return camionRepo.findAll();
    }

    public Camion obtenerPorId(Long id){
        return camionRepo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Camión no encontrado"));
        //RuntimeException("Camión no encontrado"));
    }

    public Camion crear(Camion camion){
        return camionRepo.save(camion);
    }

    public Camion actualizar(Long id, Camion camion){
        camion.setId(id);
        return camionRepo.save(camion);
    }

    public void eliminar(Long id){
        camionRepo.deleteById(id);
    }
}
