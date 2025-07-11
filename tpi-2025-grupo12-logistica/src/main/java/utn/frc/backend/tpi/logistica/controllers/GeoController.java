package utn.frc.backend.tpi.logistica.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import utn.frc.backend.tpi.logistica.dtos.TramoRutaDto;
import utn.frc.backend.tpi.logistica.services.GeoService;

@RestController
@RequestMapping("/logistica/distancia")
@RequiredArgsConstructor
public class GeoController {
    private final GeoService geoService;

    @GetMapping
    public TramoRutaDto obtenerDistancia(@RequestParam Long origenID, @RequestParam Long destinoID) throws Exception {
        return geoService.calcularDistancia(origenID, destinoID);
    }
}
