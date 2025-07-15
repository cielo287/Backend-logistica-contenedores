package utn.frc.backend.tpi.logistica.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import utn.frc.backend.tpi.logistica.dtos.ContenedorDto;
import utn.frc.backend.tpi.logistica.dtos.EstadoSolicitudDto;
import utn.frc.backend.tpi.logistica.models.Solicitud;
import utn.frc.backend.tpi.logistica.repositories.SolicitudRepository;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepo;

    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${tpi-api-gw.url-pedidos}")
    private String baseUrl;

    public List<Solicitud> obtenerTodas() {
        return solicitudRepo.findAll();
    }

    public Solicitud obtenerPorId(Long id) {
        return solicitudRepo.findById(id).orElse(null);
    }

    public Solicitud crear(Solicitud solicitud) {

        String contenedorUrl = baseUrl + "/contenedores/" + solicitud.getContenedorId();
        var contenedor = restTemplate.getForObject(contenedorUrl, Object.class);
        if (contenedor == null) {
            throw new RuntimeException("El contenedor no existe");
        }

        String camionUrl = baseUrl + "/camiones/" + solicitud.getCamionId();
        var camion = restTemplate.getForObject(camionUrl, Object.class);
        if (camion == null) {
            throw new RuntimeException("El camión no existe");
        }

        return solicitudRepo.save(solicitud);
        }

        
    public Solicitud actualizar(Long id, Solicitud solicitud) {
            solicitud.setId(id);
            return solicitudRepo.save(solicitud);
    }


    public void eliminar(Long id) {
        solicitudRepo.deleteById(id);
    }


    // METODO PARA OBTENER LAS SOLICITUDES SEGUN EL ESTADO
    public EstadoSolicitudDto obtenerEstadoSolicitud(Long solicitudId,Long clienteId) {
    Solicitud solicitud = solicitudRepo.findById(solicitudId)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

    String contenedorUrl = baseUrl + "/contenedores/" + solicitud.getContenedorId();
    ContenedorDto contenedor = restTemplate.getForObject(contenedorUrl, ContenedorDto.class);

    if (contenedor == null) {
        throw new RuntimeException("Contenedor no encontrado");
    }

    if (!contenedor.getClienteId().equals(clienteId)) {
        throw new RuntimeException("El cliente no tiene acceso a esta solicitud");
    }

    return new EstadoSolicitudDto(
            solicitud.getId(),
            contenedor.getId(),
            contenedor.getClienteId(),
            contenedor.getEstado().getNombre()
    );
}
}
