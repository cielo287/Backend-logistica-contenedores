package utn.frc.backend.tpi.logistica.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utn.frc.backend.tpi.logistica.models.Solicitud;
import utn.frc.backend.tpi.logistica.repositories.SolicitudRepository;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepo;


    public List<Solicitud> obtenerTodas() {
        return solicitudRepo.findAll();
    }

    public Solicitud obtenerPorId(Long id) {
        return solicitudRepo.findById(id).orElse(null);
    }

    public Solicitud crear(Solicitud solicitud) {
        return solicitudRepo.save(solicitud);
    }

    public Solicitud actualizar(Long id, Solicitud solicitud) {
        solicitud.setId(id);
        return solicitudRepo.save(solicitud);
    }

    public void eliminar(Long id) {
        solicitudRepo.deleteById(id);
    }
}
