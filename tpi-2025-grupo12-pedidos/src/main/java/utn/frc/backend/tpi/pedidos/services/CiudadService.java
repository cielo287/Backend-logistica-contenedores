package utn.frc.backend.tpi.pedidos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utn.frc.backend.tpi.pedidos.models.Ciudad;
import utn.frc.backend.tpi.pedidos.repositories.CiudadRepository;

@Service
public class CiudadService {
    
    @Autowired
    private CiudadRepository ciudadRepo;
    
    public List<Ciudad> obtenerTodos(){
        return ciudadRepo.findAll();
    }

    public Ciudad obtenerPorId(Long id){
        return ciudadRepo.findById(id).orElse(null);
    }

    public Ciudad crear(Ciudad ciudad){
        return ciudadRepo.save(ciudad);
    }

    public Ciudad actualizar(Long id, Ciudad ciudad){
        ciudad.setId(id);
        return ciudadRepo.save(ciudad);
    }

    public void eliminar(Long id){
        ciudadRepo.deleteById(id);
    }
}
