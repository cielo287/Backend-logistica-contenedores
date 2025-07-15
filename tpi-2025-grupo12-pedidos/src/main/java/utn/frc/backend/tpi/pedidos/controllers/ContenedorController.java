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
import utn.frc.backend.tpi.pedidos.dto.ContenedorDTO;
import utn.frc.backend.tpi.pedidos.mapper.ContenedorMapper;
import utn.frc.backend.tpi.pedidos.models.Contenedor;
import utn.frc.backend.tpi.pedidos.services.ContenedorService;

@RestController
@RequestMapping("/contenedores")

public class ContenedorController {

    @Autowired
    private ContenedorService contenedorService;

    @Autowired
    private ContenedorMapper contenedorMapper;
    
    @GetMapping
    public List<ContenedorDTO> listar() {
        return contenedorService.obtenerTodos().stream().map(contenedorMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ContenedorDTO listarPorId(@PathVariable Long id) {
        Contenedor contenedor = contenedorService.obtenerPorId(id);
        return contenedorMapper.toDTO(contenedor);

    }

    @PostMapping
    public ContenedorDTO crear(@RequestBody ContenedorDTO contenedorDTO) {
        Contenedor contenedor = contenedorMapper.toEntity(contenedorDTO);
        Contenedor guardado = contenedorService.crear(contenedor);
        return contenedorMapper.toDTO(guardado);
        
    }

    @PutMapping("/{id}")
    public ContenedorDTO actualizar(@PathVariable Long id, @RequestBody ContenedorDTO contenedorDTO){
        Contenedor contenedorActualizado = contenedorService.actualizar(id, contenedorMapper.toEntity(contenedorDTO));
        return contenedorMapper.toDTO(contenedorActualizado);}



    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        contenedorService.eliminar(id);
    }

    // CONTROLLADOR DE CONTENEDOR POR ESTADO
    @GetMapping("/pendientes")
    public List<Contenedor> obtenerContenedoresPendientes() {
        return contenedorService.obtenerPendientesEntrega();
    }

}
