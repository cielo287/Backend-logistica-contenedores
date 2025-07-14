package utn.frc.backend.tpi.logistica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frc.backend.tpi.logistica.dtos.TramoRutaDto;
import utn.frc.backend.tpi.logistica.services.GeoService;

@RestController
@RequestMapping("/logistica/distancia")
public class GeoController {
    @Autowired
    private GeoService geoService;

    // Endpoints para cálculo de distancias
    @GetMapping("/ciudades")
    public ResponseEntity<TramoRutaDto> calcularDistanciaEntreCiudades(
            @RequestParam Long origenId,
            @RequestParam Long destinoId) {
        try {
            TramoRutaDto tramoRuta = geoService.calcularDistanciaEntreCiudades(origenId, destinoId);
            return ResponseEntity.ok(tramoRuta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/depositos")
    public ResponseEntity<TramoRutaDto> calcularDistanciaEntreDepositos(
            @RequestParam Long origenId,
            @RequestParam Long destinoId) {
        try {
            TramoRutaDto tramoRuta = geoService.calcularDistanciaEntreDepositos(origenId, destinoId);
            return ResponseEntity.ok(tramoRuta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/ciudad-deposito")
    public ResponseEntity<TramoRutaDto> calcularDistanciaCiudadADeposito(
            @RequestParam Long ciudadId,
            @RequestParam Long depositoId) {
        try {
            TramoRutaDto tramoRuta = geoService.calcularDistanciaCiudadADeposito(ciudadId, depositoId);
            return ResponseEntity.ok(tramoRuta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/deposito-ciudad")
    public ResponseEntity<TramoRutaDto> calcularDistanciaDepositoACiudad(
            @RequestParam Long depositoId,
            @RequestParam Long ciudadId) {
        try {
            TramoRutaDto tramoRuta = geoService.calcularDistanciaDepositoACiudad(depositoId, ciudadId);
            return ResponseEntity.ok(tramoRuta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Endpoint más flexible
    @GetMapping("/flexible")
    public ResponseEntity<TramoRutaDto> calcularDistanciaFlexible(
            @RequestParam Long origenId,
            @RequestParam String origenTipo,
            @RequestParam Long destinoId,
            @RequestParam String destinoTipo) {
        try {
            TramoRutaDto tramoRuta = geoService.calcularDistanciaFlexible(origenId, origenTipo, destinoId, destinoTipo);
            return ResponseEntity.ok(tramoRuta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
