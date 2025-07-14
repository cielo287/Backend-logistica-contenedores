package utn.frc.backend.tpi.logistica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import utn.frc.backend.tpi.logistica.models.TramoRuta;
import utn.frc.backend.tpi.logistica.services.TramoRutaService;

@RestController
@RequestMapping("/tramos-ruta")
public class TramoRutaController {

    @Autowired
    private TramoRutaService tramoRutaService;

    @GetMapping
    public List<TramoRuta> listar() {
        return tramoRutaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public TramoRuta listarPorId(@PathVariable Long id) {
        return tramoRutaService.obtenerPorId(id);
    }

    @PostMapping
    public TramoRuta crear(@RequestBody TramoRuta tramoRuta) {
        return tramoRutaService.crear(tramoRuta);
    }

    @PutMapping("/{id}")
    public TramoRuta actualizar(@PathVariable Long id, @RequestBody TramoRuta tramoRuta) {
        return tramoRutaService.actualizar(id, tramoRuta);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        tramoRutaService.eliminar(id);
    }

}
