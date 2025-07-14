package utn.frc.backend.tpi.logistica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import utn.frc.backend.tpi.logistica.models.Tarifa;
import utn.frc.backend.tpi.logistica.services.TarifaService;

@RestController
@RequestMapping("/tarifas")
public class TarifaController {

    @Autowired
    private TarifaService tarifaService;

    @GetMapping
    public List<Tarifa> listar() {
        return tarifaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Tarifa listarPorId(@PathVariable Long id) {
        return tarifaService.obtenerPorId(id);
    }

    @PostMapping
    public Tarifa crear(@RequestBody Tarifa tarifa) {
        return tarifaService.crear(tarifa);
    }

    @PutMapping("/{id}")
    public Tarifa actualizar(@PathVariable Long id, @RequestBody Tarifa tarifa) {
        return tarifaService.actualizar(id, tarifa);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        tarifaService.eliminar(id);
    }
}
