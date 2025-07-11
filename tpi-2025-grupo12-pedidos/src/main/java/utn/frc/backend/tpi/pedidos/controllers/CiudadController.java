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

import utn.frc.backend.tpi.pedidos.dto.CiudadDto;
import utn.frc.backend.tpi.pedidos.models.Ciudad;
import utn.frc.backend.tpi.pedidos.services.CiudadService;

@RestController
@RequestMapping("/api/pedidos/ciudades")
public class CiudadController {

    @Autowired
    private CiudadService ciudadServicio;

    @GetMapping
    public List<Ciudad> listar() {
        return ciudadServicio.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CiudadDto> listarPorId(@PathVariable Long id) {
        Ciudad ciudad = ciudadServicio.obtenerPorId(id);
        if (ciudad == null) {
            return ResponseEntity.notFound().build();
        }

        CiudadDto dto = new CiudadDto(
                ciudad.getId(),
                ciudad.getNombre(),
                ciudad.getLatitud(),
                ciudad.getLongitud());

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public Ciudad crear(@RequestBody Ciudad ciudad) {
        return ciudadServicio.crear(ciudad);
    }

    @PutMapping("/{id}")
    public Ciudad actualizar(@PathVariable Long id, @RequestBody Ciudad ciudad) {
        return ciudadServicio.actualizar(id, ciudad);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        ciudadServicio.eliminar(id);
    }
}
