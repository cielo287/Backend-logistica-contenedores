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


import utn.frc.backend.tpi.pedidos.models.Contenedor;
import utn.frc.backend.tpi.pedidos.services.ContenedorService;


@RestController
@RequestMapping("/api/pedidos/contenedores")

public class ContenedorController {

    @Autowired
    private ContenedorService contenedorService;
    
    @GetMapping
    public List<Contenedor> listar() {
        return contenedorService.obtenerTodos();
    }
    
    @GetMapping("/{id}")
    public Contenedor listarPorId(@PathVariable Long id) {
        return contenedorService.obtenerPorId(id);
    }

    @PostMapping
    public Contenedor crear(@RequestBody Contenedor contenedor) {
        return contenedorService.crear(contenedor);
    }

    @PutMapping("/{id}")
    public Contenedor actualizar(@PathVariable Long id, @RequestBody Contenedor contenedor){
        return contenedorService.actualizar(id, contenedor);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        contenedorService.eliminar(id);
    }
    

}
