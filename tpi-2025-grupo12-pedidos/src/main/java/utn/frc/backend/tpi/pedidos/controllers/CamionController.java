package utn.frc.backend.tpi.pedidos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import utn.frc.backend.tpi.pedidos.dto.CamionDTO;
import utn.frc.backend.tpi.pedidos.models.Camion;
import utn.frc.backend.tpi.pedidos.services.CamionService;

@RestController
@RequestMapping("/camiones")
public class CamionController {

    @Autowired
    private CamionService camionServicio;

    @GetMapping
    public List<Camion> listar() {
        return camionServicio.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CamionDTO> listarPorId(@PathVariable Long id) {
        Camion camion = camionServicio.obtenerPorId(id);
        if (camion != null) {
            return ResponseEntity.ok(new CamionDTO(camion));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Camion crear(@RequestBody Camion camion) {
        return camionServicio.crear(camion);
    }

    @PutMapping("/{id}")
    public Camion actualizar(@PathVariable Long id, @RequestBody Camion camion) {
        return camionServicio.actualizar(id, camion);

    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        camionServicio.eliminar(id);
    }

}
