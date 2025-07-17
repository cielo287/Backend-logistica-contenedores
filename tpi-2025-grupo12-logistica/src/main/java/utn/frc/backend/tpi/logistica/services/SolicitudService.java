package utn.frc.backend.tpi.logistica.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import utn.frc.backend.tpi.logistica.dtos.CamionDto;
import utn.frc.backend.tpi.logistica.dtos.ContenedorDto;
import utn.frc.backend.tpi.logistica.dtos.EstadoSolicitudDto;
import utn.frc.backend.tpi.logistica.dtos.TramoRutaDto;
import utn.frc.backend.tpi.logistica.models.Solicitud;
import utn.frc.backend.tpi.logistica.models.TramoRuta;
import utn.frc.backend.tpi.logistica.repositories.SolicitudRepository;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepo;

    @Autowired
    private TarifaService tarifaService;

    @Autowired
    private GeoService geoService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${servicio.pedidos.url:http://localhost:8082/api/pedidos}")
    private String baseUrl;

    public List<Solicitud> obtenerTodas() {
        return solicitudRepo.findAll();
    }

    public Solicitud obtenerPorId(Long id) {

        return solicitudRepo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró solicitud con id " + id));
    }

    private void validarPesos(ContenedorDto contenedor, CamionDto camion) {
    if (contenedor.getPeso() > camion.getCapacidadPeso()) {
        throw new RuntimeException("El peso del contenedor excede la capacidad del camión.");
    }
    if (contenedor.getVolumen() > camion.getVolumen()) {
        throw new RuntimeException("El volumen del contenedor excede la capacidad del camión.");
    }
    }


    public Solicitud crear(Solicitud solicitud) {
        // Validaciones básicas de existencia camion y contenedor
        String contenedorUrl = baseUrl + "/contenedores/" + solicitud.getContenedorId();
        ContenedorDto contenedor = restTemplate.getForObject(contenedorUrl, ContenedorDto.class);
        if (contenedor == null)
            throw new RuntimeException("Contenedor no encontrado");

        String camionUrl = baseUrl + "/camiones/" + solicitud.getCamionId();
        CamionDto camion = restTemplate.getForObject(camionUrl, CamionDto.class);
        if (camion == null)
            throw new RuntimeException("Camión no encontrado");

        //Validar que el peso del contenedor no supere el del camion
        validarPesos(contenedor, camion);

        List<TramoRuta> tramos = new ArrayList<>();

        try {
            if (solicitud.getDepositoId() != null) {
                // Tramo Ciudad → Depósito
                TramoRutaDto tramo1Dto = geoService.calcularDistanciaCiudadADeposito(
                        solicitud.getCiudadOrigenId(), solicitud.getDepositoId());

                TramoRuta tramo1 = new TramoRuta();
                tramo1.setOrden(1);
                tramo1.setUbicacionOrigenId(solicitud.getCiudadOrigenId());
                tramo1.setUbicacionDestinoId(solicitud.getDepositoId());
                tramo1.setDistancia(tramo1Dto.getDistancia());
                tramo1.setTiempoEstimado(tramo1Dto.getTiempoEstimado());
                tramo1.setSolicitud(solicitud);
                tramos.add(tramo1);

                // Tramo Depósito → Ciudad destino
                TramoRutaDto tramo2Dto = geoService.calcularDistanciaDepositoACiudad(
                        solicitud.getDepositoId(), solicitud.getCiudadDestinoId());

                TramoRuta tramo2 = new TramoRuta();
                tramo2.setOrden(2);
                tramo2.setUbicacionOrigenId(solicitud.getDepositoId());
                tramo2.setUbicacionDestinoId(solicitud.getCiudadDestinoId());
                tramo2.setDistancia(tramo2Dto.getDistancia());
                tramo2.setTiempoEstimado(tramo2Dto.getTiempoEstimado());
                tramo2.setSolicitud(solicitud);
                tramos.add(tramo2);
            } else {
                // Tramo único Ciudad → Ciudad
                TramoRutaDto tramoDto = geoService.calcularDistanciaEntreCiudades(
                        solicitud.getCiudadOrigenId(), solicitud.getCiudadDestinoId());

                TramoRuta tramo = new TramoRuta();
                tramo.setOrden(1);
                tramo.setUbicacionOrigenId(solicitud.getCiudadOrigenId());
                tramo.setUbicacionDestinoId(solicitud.getCiudadDestinoId());
                tramo.setDistancia(tramoDto.getDistancia());
                tramo.setTiempoEstimado(tramoDto.getTiempoEstimado());
                tramo.setSolicitud(solicitud);
                tramos.add(tramo);
            }

            solicitud.setTramos(tramos);

            // Calcular costo estimado usando tarifaService
            double costo = tarifaService.calcularTarifaSolicitud(solicitud);
            solicitud.setCostoEstimado(costo);

            // Calcular tiempo estimado total (horas)
            double tiempoTotal = tramos.stream()
                    .filter(t -> t.getTiempoEstimado() != null)
                    .mapToDouble(TramoRuta::getTiempoEstimado)
                    .sum();
            solicitud.setTiempoEstimadoHoras(tiempoTotal);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al calcular tramos o tarifa: " + e.getMessage());
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
    public EstadoSolicitudDto obtenerEstadoSolicitud(Long solicitudId, Long clienteId) {
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
                contenedor.getEstado().getNombre());
    }
}
