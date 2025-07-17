package utn.frc.backend.tpi.pedidos.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import utn.frc.backend.tpi.pedidos.dto.EstadoSimpleDto;
import utn.frc.backend.tpi.pedidos.models.Cliente;
import utn.frc.backend.tpi.pedidos.models.Contenedor;
import utn.frc.backend.tpi.pedidos.models.Estado;
import utn.frc.backend.tpi.pedidos.models.HistorialEstado;
import utn.frc.backend.tpi.pedidos.repositories.ClienteRepository;
import utn.frc.backend.tpi.pedidos.repositories.ContenedorRepository;
import utn.frc.backend.tpi.pedidos.repositories.EstadoRepository;
import utn.frc.backend.tpi.pedidos.repositories.HistorialEstadoRepository;

@Service
public class ContenedorService {
    
    @Autowired
    private ContenedorRepository contenedorRepo;

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private EstadoRepository estadoRepo;

    @Autowired
    private HistorialEstadoRepository historialEstadoRepo;

    public List<Contenedor> obtenerTodos(){
        return contenedorRepo.findAll();
    }

    public Contenedor obtenerPorId(Long id){
        //return contenedorRepo.findById(id).orElseThrow(() -> new RuntimeException("Contenedor no encontrado"));
        return contenedorRepo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contenedor no encontrado"));

    }

    public Contenedor crear(Contenedor contenedor){
        //Buscar cliente existente
        Long clienteId = contenedor.getCliente().getId();
        Cliente cliente = clienteRepo.findById(clienteId)
        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        //Buscar estado existente
        Long estadoId = contenedor.getEstado().getId();
        Estado estado = estadoRepo.findById(estadoId)
        .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
        
        contenedor.setCliente(cliente);
        contenedor.setEstado(estado);
        
        return contenedorRepo.save(contenedor);
    }

    public Contenedor actualizar(Long id, Contenedor contenedor){
        contenedor.setId(id);
        return contenedorRepo.save(contenedor);
    }

    public void eliminar(Long id){
        contenedorRepo.deleteById(id);
    }

    //CONSULTA DE CONTENEDORES POR ESTADO NO ENTREGADO
    public List<Contenedor> obtenerPendientesEntrega() {
    return contenedorRepo.findByEstadoNombreNot("Entregado en destino");
    }

    //METODO DE ACTUALIZAR EL AVANCE DEL CONTENEDOR
    public Contenedor actualizarEstado(Long contenedorId, Long estadoId) {

        Contenedor contenedor = obtenerPorId(contenedorId);
        Estado estado = estadoRepo.findById(estadoId)
            .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        contenedor.setEstado(estado);
        contenedorRepo.save(contenedor);

        // Guardar en historial
        HistorialEstado historial = new HistorialEstado();
        historial.setContenedor(contenedor);
        historial.setEstado(estado);
        historial.setFechaCambio(LocalDate.now());
        historialEstadoRepo.save(historial);

        return contenedor;
    }

    
    //METODO PARA DEVOLVER EL DTO DEL HISTORIAL
    public List<EstadoSimpleDto> obtenerHistorialSimplificado(Long contenedorId) {
        List<HistorialEstado> historialOrdenado = historialEstadoRepo.findByContenedorIdOrderByFechaCambioAsc(contenedorId);

        List<EstadoSimpleDto> historialSimplificado = historialOrdenado.stream()
            .map(registro -> new EstadoSimpleDto(
                registro.getEstado().getNombre(),
                registro.getFechaCambio()
            ))
            .toList();
        return historialSimplificado;
    }



}
