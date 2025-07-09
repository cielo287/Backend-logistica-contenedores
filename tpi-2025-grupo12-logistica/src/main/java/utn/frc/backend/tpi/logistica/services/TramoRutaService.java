package utn.frc.backend.tpi.logistica.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utn.frc.backend.tpi.logistica.models.TramoRuta;
import utn.frc.backend.tpi.logistica.repositories.TramoRutaRepository;

@Service
public class TramoRutaService {

    @Autowired
    private TramoRutaRepository tramoRutaRepo;

    public List<TramoRuta> obtenerTodos() {
        return tramoRutaRepo.findAll();
    }

    public TramoRuta obtenerPorId(Long id) {
        return tramoRutaRepo.findById(id).orElse(null);
    }

    public TramoRuta crear(TramoRuta tramoRuta) {
        return tramoRutaRepo.save(tramoRuta);
    }

    public TramoRuta actualizar(Long id, TramoRuta tramoRuta) {
        tramoRuta.setId(id);
        return tramoRutaRepo.save(tramoRuta);
    }

    public void eliminar(Long id) {
        tramoRutaRepo.deleteById(id);
    }
}
