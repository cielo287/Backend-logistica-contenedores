package utn.frc.backend.tpi.pedidos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;
import utn.frc.backend.tpi.pedidos.models.Estado;
import utn.frc.backend.tpi.pedidos.services.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoService estadoServicio;

    @GetMapping
    public List<Estado> listar(){
        return estadoServicio.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Estado listarPorId(@PathVariable Long id){
        return estadoServicio.obtenerPorId(id);
    }

    @PostMapping
    public Estado crear(@RequestBody Estado estado){
        return estadoServicio.crear(estado);
    }

    @PutMapping("/{id}")
    public Estado actualizar(@PathVariable Long id, @RequestBody Estado estado){
        return estadoServicio.actualizar(id, estado);
    }


    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        estadoServicio.eliminar(id);
    }

    
}
