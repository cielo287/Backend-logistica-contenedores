package utn.frc.backend.tpi.pedidos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import utn.frc.backend.tpi.pedidos.models.Cliente;
import utn.frc.backend.tpi.pedidos.models.Contenedor;
import utn.frc.backend.tpi.pedidos.models.Estado;
import utn.frc.backend.tpi.pedidos.repositories.ClienteRepository;
import utn.frc.backend.tpi.pedidos.repositories.ContenedorRepository;
import utn.frc.backend.tpi.pedidos.repositories.EstadoRepository;

@Service
public class ContenedorService {
    
    @Autowired
    private ContenedorRepository contenedorRepo;

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private EstadoRepository estadoRepo;


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


}
