package utn.frc.backend.tpi.logistica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import utn.frc.backend.tpi.logistica.dtos.EstadoSolicitudDto;
import utn.frc.backend.tpi.logistica.models.Solicitud;
import utn.frc.backend.tpi.logistica.services.SolicitudService;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;

    @GetMapping
    public List<Solicitud> listar() {
        return solicitudService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Solicitud listarPorId(@PathVariable Long id) {
        return solicitudService.obtenerPorId(id);
    }

    @PostMapping
    public Solicitud crear(@RequestBody Solicitud solicitud) {
        return solicitudService.crear(solicitud);
    }

    @PutMapping("/{id}")
    public Solicitud actualizar(@PathVariable Long id, @RequestBody Solicitud solicitud) {
        return solicitudService.actualizar(id, solicitud);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        solicitudService.eliminar(id);
    }

    // CONTROLLADOR DE SOLICITUD POR ESTADO
    @GetMapping("/clientes/{clienteId}/solicitudes/{solicitudId}/estado")
    public EstadoSolicitudDto obtenerEstadoSolicitud(
        @PathVariable Long clienteId,
        @PathVariable Long solicitudId
        ) 
    {
    return solicitudService.obtenerEstadoSolicitud(solicitudId, clienteId);
    }


}
