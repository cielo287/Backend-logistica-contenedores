package utn.frc.backend.tpi.logistica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.frc.backend.tpi.logistica.services.GeoService;
import utn.frc.backend.tpi.logistica.dtos.CiudadDto;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/logistica/ciudades")
public class CiudadProxyController {

    @Autowired
    private GeoService geoService;

    @GetMapping("/{id}")
    public ResponseEntity<CiudadDto> obtenerCiudad(@PathVariable Long id) {
        try {
            CiudadDto ciudad = geoService.getCiudadById(id);
            return ResponseEntity.ok(ciudad);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
