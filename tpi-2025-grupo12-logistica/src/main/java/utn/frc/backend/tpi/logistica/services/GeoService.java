package utn.frc.backend.tpi.logistica.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import utn.frc.backend.tpi.logistica.dtos.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.client.RestTemplate;
import utn.frc.backend.tpi.logistica.interfaces.Ubicable;

/*
 * @Service
 * 
 * @RequiredArgsConstructor
 * public class GeoService {
 * 
 * @Value("${google.maps.apikey}")
 * private String apiKey;
 * 
 * private final RestTemplate restTemplate;
 * 
 * @Value("${servicio.ciudades.url}")
 * private String ciudadesBaseUrl;
 * 
 * public CiudadDto getCiudadById(Long id) {
 * String url = ciudadesBaseUrl + "/ciudades/" + id;
 * System.out.println("Llamando a: " + url);
 * try {
 * return restTemplate.getForObject(url, CiudadDto.class);
 * } catch (Exception e) {
 * System.out.println("Error al llamar al servicio: " + e.getMessage());
 * throw e;
 * }
 * }
 * 
 * public TramoRutaDto calcularDistancia(Long origenId, Long destinoId) throws
 * Exception {
 * CiudadDto origen = getCiudadById(origenId);
 * CiudadDto destino = getCiudadById(destinoId);
 * 
 * String origenStr = origen.getLat() + "," + origen.getLon();
 * String destinoStr = destino.getLat() + "," + destino.getLon();
 * 
 * String url =
 * "https://maps.googleapis.com/maps/api/distancematrix/json?origins="
 * + origenStr + "&destinations=" + destinoStr + "&units=metric&key=" + apiKey;
 * 
 * String response = restTemplate.getForObject(url, String.class);
 * 
 * ObjectMapper mapper = new ObjectMapper();
 * JsonNode root = mapper.readTree(response);
 * JsonNode leg = root.path("rows").get(0).path("elements").get(0);
 * 
 * TramoRutaDto dto = new TramoRutaDto();
 * dto.setOrigen(origenId);
 * dto.setDestino(destinoId);
 * dto.setDistancia(leg.path("distance").path("value").asDouble() / 1000); // km
 * dto.setTiempoEstimado(leg.path("duration").path("value").asDouble() /
 * 3600.0); // horas
 * 
 * return dto;
 * }
 * }
 */
@Service
@RequiredArgsConstructor
public class GeoService {

    @Value("${google.maps.apikey}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Value("${servicio.ciudades.url}")
    private String ciudadesBaseUrl;

    @Value("${servicio.depositos.url}")
    private String depositosBaseUrl;

    // Métodos existentes
    public CiudadDto getCiudadById(Long id) {
        String url = ciudadesBaseUrl + "/ciudades/" + id;
        System.out.println("Llamando a: " + url);
        try {
            return restTemplate.getForObject(url, CiudadDto.class);
        } catch (Exception e) {
            System.out.println("Error al llamar al servicio: " + e.getMessage());
            throw e;
        }
    }

    // Nuevo método para obtener depósitos
    public DepositoDto getDepositoById(Long id) {
        String url = depositosBaseUrl + "/depositos/" + id + "/dto";
        System.out.println("Llamando a depósito: " + url);
        try {
            DepositoDto deposito = restTemplate.getForObject(url, DepositoDto.class);
            System.out.println("Depósito obtenido: " + deposito);
            return deposito;
        } catch (Exception e) {
            System.out.println("Error al llamar al servicio de depósitos: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // Método genérico para obtener cualquier ubicable
    public Ubicable getUbicableById(Long id, String tipo) {
        if ("CIUDAD".equals(tipo)) {
            return getCiudadById(id);
        } else if ("DEPOSITO".equals(tipo)) {
            return getDepositoById(id);
        } else {
            throw new IllegalArgumentException("Tipo no válido: " + tipo);
        }
    }

    // Método original mantenido para compatibilidad
    public TramoRutaDto calcularDistancia(Long origenId, Long destinoId) throws Exception {
        return calcularDistanciaEntreCiudades(origenId, destinoId);
    }

    // Método específico para ciudades
    public TramoRutaDto calcularDistanciaEntreCiudades(Long origenId, Long destinoId) throws Exception {
        CiudadDto origen = getCiudadById(origenId);
        CiudadDto destino = getCiudadById(destinoId);

        TramoRutaDto dto = calcularDistanciaEntreUbicables(origen, destino);
        dto.setOrigenId(origenId);
        dto.setOrigenTipo("CIUDAD");
        dto.setDestinoId(destinoId);
        dto.setDestinoTipo("CIUDAD");

        return dto;
    }

    // Método específico para depósitos
    public TramoRutaDto calcularDistanciaEntreDepositos(Long origenId, Long destinoId) throws Exception {
        DepositoDto origen = getDepositoById(origenId);
        DepositoDto destino = getDepositoById(destinoId);

        TramoRutaDto dto = calcularDistanciaEntreUbicables(origen, destino);
        dto.setOrigenId(origenId);
        dto.setOrigenTipo("DEPOSITO");
        dto.setDestinoId(destinoId);
        dto.setDestinoTipo("DEPOSITO");

        return dto;
    }

    // Método mixto: ciudad a depósito
    public TramoRutaDto calcularDistanciaCiudadADeposito(Long ciudadId, Long depositoId) throws Exception {
        CiudadDto origen = getCiudadById(ciudadId);
        DepositoDto destino = getDepositoById(depositoId);

        TramoRutaDto dto = calcularDistanciaEntreUbicables(origen, destino);
        dto.setOrigenId(ciudadId);
        dto.setOrigenTipo("CIUDAD");
        dto.setDestinoId(depositoId);
        dto.setDestinoTipo("DEPOSITO");

        return dto;
    }

    // Método mixto: depósito a ciudad
    public TramoRutaDto calcularDistanciaDepositoACiudad(Long depositoId, Long ciudadId) throws Exception {
        DepositoDto origen = getDepositoById(depositoId);
        CiudadDto destino = getCiudadById(ciudadId);

        TramoRutaDto dto = calcularDistanciaEntreUbicables(origen, destino);
        dto.setOrigenId(depositoId);
        dto.setOrigenTipo("DEPOSITO");
        dto.setDestinoId(ciudadId);
        dto.setDestinoTipo("CIUDAD");

        return dto;
    }

    // Método flexible
    public TramoRutaDto calcularDistanciaFlexible(Long origenId, String origenTipo, Long destinoId, String destinoTipo)
            throws Exception {
        Ubicable origen = getUbicableById(origenId, origenTipo);
        Ubicable destino = getUbicableById(destinoId, destinoTipo);

        TramoRutaDto dto = calcularDistanciaEntreUbicables(origen, destino);
        dto.setOrigenId(origenId);
        dto.setOrigenTipo(origenTipo);
        dto.setDestinoId(destinoId);
        dto.setDestinoTipo(destinoTipo);

        return dto;
    }

    // Método privado que hace el cálculo real con Google Maps
    private TramoRutaDto calcularDistanciaEntreUbicables(Ubicable origen, Ubicable destino) throws Exception {
        String origenStr = origen.getLat() + "," + origen.getLon();
        String destinoStr = destino.getLat() + "," + destino.getLon();

        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="
                + origenStr + "&destinations=" + destinoStr + "&units=metric&key=" + apiKey;

        String response = restTemplate.getForObject(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        JsonNode leg = root.path("rows").get(0).path("elements").get(0);

        TramoRutaDto dto = new TramoRutaDto();
        dto.setDistancia(leg.path("distance").path("value").asDouble() / 1000); // km
        dto.setTiempoEstimado(leg.path("duration").path("value").asDouble() / 3600.0); // horas

        return dto;
    }
}