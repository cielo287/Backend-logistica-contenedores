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

import utn.frc.backend.tpi.pedidos.dto.DepositoDto;
import utn.frc.backend.tpi.pedidos.models.Deposito;
import utn.frc.backend.tpi.pedidos.services.DepositoService;

@RestController
@RequestMapping("/depositos")
public class DepositoController {

    @Autowired
    private DepositoService depositoServicio;

    @GetMapping
    public List<Deposito> listar() {
        return depositoServicio.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Deposito listarPorId(@PathVariable Long id) {
        return depositoServicio.obtenerPorId(id);
    }

    // Para obtener un DTO de Deposito necesario para el calculo de distancias
    @GetMapping("/{id}/dto")
    public ResponseEntity<DepositoDto> obtenerDto(@PathVariable Long id) {
        Deposito deposito = depositoServicio.obtenerPorId(id);
        if (deposito == null) {
            return ResponseEntity.notFound().build();
        }

        DepositoDto dto = new DepositoDto(
                deposito.getId(),
                deposito.getCiudad().getId(),
                deposito.getDireccion(),
                deposito.getLatitud(),
                deposito.getLongitud());

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public Deposito crear(@RequestBody Deposito deposito) {
        return depositoServicio.crear(deposito);
    }

    @PutMapping("/{id}")
    public Deposito actualizar(@PathVariable Long id, @RequestBody Deposito deposito) {
        return depositoServicio.actualizar(id, deposito);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        depositoServicio.eliminar(id);
    }
}
