package utn.frc.backend.tpi.logistica.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import utn.frc.backend.tpi.logistica.models.Solicitud;
import utn.frc.backend.tpi.logistica.repositories.SolicitudRepository;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepo;

    @Autowired
    private RestTemplate restTemplate;

    public List<Solicitud> obtenerTodas() {
        return solicitudRepo.findAll();
    }

    public Solicitud obtenerPorId(Long id) {
        return solicitudRepo.findById(id).orElse(null);
    }

    public Solicitud crear(Solicitud solicitud) {

        // URL base del microservicio de pedidos (ajustá el puerto si es distinto)
        String baseUrl = "http://localhost:8080/api/pedidos";

        // Validar contenedor
        String contenedorUrl = baseUrl + "/contenedores/" + solicitud.getContenedorId();
        var contenedor = restTemplate.getForObject(contenedorUrl, Object.class);
        if (contenedor == null) {
            throw new RuntimeException("El contenedor no existe");
        }

        // Validar camión
        String camionUrl = baseUrl + "/camiones/" + solicitud.getCamionId();
        var camion = restTemplate.getForObject(camionUrl, Object.class);
        if (camion == null) {
            throw new RuntimeException("El camión no existe");
        }

        // Guardar la solicitud si todo está OK
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
