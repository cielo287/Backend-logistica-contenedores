package utn.frc.backend.tpi.logistica.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import utn.frc.backend.tpi.logistica.dtos.TramoRutaDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import utn.frc.backend.tpi.logistica.dtos.CiudadDto;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GeoService {

    @Value("${google.maps.apikey}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Value("${servicio.ciudades.url}")
    private String ciudadesBaseUrl;

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

    public TramoRutaDto calcularDistancia(Long origenId, Long destinoId) throws Exception {
        CiudadDto origen = getCiudadById(origenId);
        CiudadDto destino = getCiudadById(destinoId);

        String origenStr = origen.getLat() + "," + origen.getLon();
        String destinoStr = destino.getLat() + "," + destino.getLon();

        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="
                + origenStr + "&destinations=" + destinoStr + "&units=metric&key=" + apiKey;

        String response = restTemplate.getForObject(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        JsonNode leg = root.path("rows").get(0).path("elements").get(0);

        TramoRutaDto dto = new TramoRutaDto();
        dto.setOrigen(origenId);
        dto.setDestino(destinoId);
        dto.setDistancia(leg.path("distance").path("value").asDouble() / 1000); // km
        dto.setTiempoEstimado(leg.path("duration").path("value").asDouble() / 3600.0); // horas

        return dto;
    }
}
