package utn.frc.backend.tpi.pedidos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utn.frc.backend.tpi.pedidos.models.Deposito;
import utn.frc.backend.tpi.pedidos.services.DepositoService;

@RestController
@RequestMapping("/api/pedidos/depositos")
public class DepositoController {
    
    @Autowired
    private DepositoService depositoServicio;

    @GetMapping
    public List<Deposito> listar(){
        return depositoServicio.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Deposito listarPorId(@PathVariable Long id){
        return depositoServicio.obtenerPorId(id);
    }

    @PostMapping
    public Deposito crear(@RequestBody Deposito deposito){
        return depositoServicio.crear(deposito);
    }

    @PutMapping("/{id}")
    public Deposito actualizar(@PathVariable Long id,@RequestBody Deposito deposito){
        return depositoServicio.actualizar(id, deposito);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        depositoServicio.eliminar(id);
    }
}
