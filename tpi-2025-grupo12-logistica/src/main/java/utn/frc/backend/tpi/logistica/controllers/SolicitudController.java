package utn.frc.backend.tpi.logistica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import utn.frc.backend.tpi.logistica.dtos.EstadoSolicitudDto;
import utn.frc.backend.tpi.logistica.dtos.SolicitudDto;
import utn.frc.backend.tpi.logistica.mappers.SolicitudMapper;
import utn.frc.backend.tpi.logistica.models.Solicitud;
import utn.frc.backend.tpi.logistica.services.SolicitudService;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;

    @Autowired
    private SolicitudMapper solicitudMapper;

    @GetMapping
    public List<SolicitudDto> listar() {
        return solicitudService.obtenerTodas().stream().map(solicitudMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public SolicitudDto listarPorId(@PathVariable Long id) {
        Solicitud solicitud = solicitudService.obtenerPorId(id);
        return solicitudMapper.toDto(solicitud);
        /*if (solicitud != null) {
            return ResponseEntity.ok(solicitud);
        } else {
            return ResponseEntity.notFound().build();
        }*/
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody SolicitudDto solicitudDto) {
        try {
            Solicitud solicitud = solicitudMapper.toEntity(solicitudDto);
            Solicitud nueva = solicitudService.crear(solicitud);
            SolicitudDto rtaDto = solicitudMapper.toDto(nueva);
            return ResponseEntity.status(HttpStatus.CREATED).body(rtaDto);
   
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Error al crear solicitud: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public SolicitudDto actualizar(@PathVariable Long id, @RequestBody SolicitudDto solicitudDto) {
        Solicitud solicitudActualizada = solicitudService.actualizar(id, solicitudMapper.toEntity(solicitudDto));
        return solicitudMapper.toDto(solicitudActualizada);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        solicitudService.eliminar(id);
    }

    // CONTROLLADOR DE SOLICITUD POR ESTADO
    @GetMapping("/clientes/{clienteId}/solicitudes/{solicitudId}/estado")
    public EstadoSolicitudDto obtenerEstadoSolicitud(
            @PathVariable Long clienteId,
            @PathVariable Long solicitudId) {
        return solicitudService.obtenerEstadoSolicitud(solicitudId, clienteId);
    }

}
