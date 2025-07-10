package utn.frc.backend.tpi.pedidos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import utn.frc.backend.tpi.pedidos.models.Cliente;

import utn.frc.backend.tpi.pedidos.services.ClienteService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/pedidos/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    private List<Cliente> listar() {
        return clienteService.obtenerTodos();

    }

    @GetMapping("/{id}")
    public Cliente listarPorId(@PathVariable Long id) {
        return clienteService.obtenerPorId(id);
    }

    @PostMapping
    public Cliente crear(@RequestBody Cliente cliente) {
        return clienteService.crear(cliente);
        
    }

    @PutMapping("/{id}")
    public Cliente actualizar(@PathVariable Long id, @RequestBody Cliente cliente) {       
        return clienteService.actualizar(id, cliente);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        clienteService.eliminar(id);
    }

    



}
