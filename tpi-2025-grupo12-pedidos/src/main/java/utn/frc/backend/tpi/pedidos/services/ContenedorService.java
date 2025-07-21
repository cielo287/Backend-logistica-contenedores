package utn.frc.backend.tpi.pedidos.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import utn.frc.backend.tpi.pedidos.dto.ContenedorRequestDTO;
import utn.frc.backend.tpi.pedidos.dto.EstadoSimpleDto;
import utn.frc.backend.tpi.pedidos.dto.NotificarCambioEstadoDto;
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

    @Autowired
    private RestTemplate restTemplate;

    public static final String ESTADO_FINAL = "Entregado en destino";

    public List<Contenedor> obtenerTodos() {
        return contenedorRepo.findAll();
    }

    public Contenedor obtenerPorId(Long id) {
        return contenedorRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contenedor no encontrado"));
    }

    // Usado por el viejo ContenedorDTO (entity completa)
    public Contenedor crear(Contenedor contenedor) {
        if (contenedor.getCliente() == null || contenedor.getCliente().getId() == null ||
            contenedor.getEstado() == null || contenedor.getEstado().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente y estado deben tener un ID válido.");
        }

        return crearContenedor(
            contenedor.getPeso(),
            contenedor.getVolumen(),
            contenedor.getCliente().getId(),
            contenedor.getEstado().getId()
        );
    }

    // Usado por el nuevo ContenedorRequestDTO (más simple)
    public Contenedor crearDesdeDto(ContenedorRequestDTO dto) {
        return crearContenedor(dto.getPeso(), dto.getVolumen(), dto.getClienteId(), dto.getEstadoId());
    }

    // Método privado reutilizado por ambos
    private Contenedor crearContenedor(double peso, double volumen, Long clienteId, Long estadoId) {
        if (peso <= 0 || volumen <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Peso y volumen deben ser mayores a 0.");
        }

        Cliente cliente = clienteRepo.findById(clienteId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente no encontrado"));

        Estado estado = estadoRepo.findById(5L).
        orElseThrow(() -> new RuntimeException());

        Contenedor contenedor = new Contenedor();
        contenedor.setPeso(peso);
        contenedor.setVolumen(volumen);
        contenedor.setCliente(cliente);
        contenedor.setEstado(estado);

        Contenedor guardado = contenedorRepo.save(contenedor);

        // Guardar en historial
        HistorialEstado historial = new HistorialEstado();
        historial.setContenedor(guardado);
        historial.setEstado(estado);
        historial.setFechaCambio(LocalDate.now());
        historialEstadoRepo.save(historial);

        return guardado;
    }

    public Contenedor actualizar(Long id, Contenedor contenedor) {
        validarContenedor(contenedor);
        contenedor.setId(id);
        return contenedorRepo.save(contenedor);
    }

    public void eliminar(Long id) {
        contenedorRepo.deleteById(id);
    }

    //METODO PARA LISTAR LOS ESTADOS QUE ESTAN EN PENDIENTE DE ENTREGA
    public List<Contenedor> obtenerPendientesEntrega() {
        return contenedorRepo.findByEstadoNombreNot(ESTADO_FINAL);
    }

    // METODO PARA ACTUALIZAR EL ESTADO DEL CONTENEDOR Y GUARDAR EN HISTORIAL
    public Contenedor actualizarEstado(Long contenedorId, Long estadoId) {
        Contenedor contenedor = obtenerPorId(contenedorId);

        Estado estado = estadoRepo.findById(estadoId)
            .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        contenedor.setEstado(estado);
        contenedorRepo.save(contenedor);

        HistorialEstado historial = new HistorialEstado();
        historial.setContenedor(contenedor);
        historial.setEstado(estado);
        historial.setFechaCambio(LocalDate.now());
        historialEstadoRepo.save(historial);
        
        //Notificar a Logistica que se hizo un cambio de estado
        restTemplate.postForEntity(
        "http://localhost:8082/api/logistica/tramos-ruta/observer/estado",
        new NotificarCambioEstadoDto(contenedorId, estado.getId(), historial.getFechaCambio()),
        Void.class);


        return contenedor;
    }

    //METODO PARA ACCEDER AL HISTORIAL
    public List<EstadoSimpleDto> obtenerHistorialSimplificado(Long contenedorId) {
        return historialEstadoRepo.findByContenedorIdOrderByFechaCambioAsc(contenedorId).stream()
            .map(registro -> new EstadoSimpleDto(
                registro.getEstado().getNombre(),
                registro.getFechaCambio()
            )).toList();
    }

    //VALIDACIONDES DE CONTENEDOR
    private void validarContenedor(Contenedor contenedor) {
        if (contenedor.getPeso() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El peso debe ser mayor a cero.");
        }

        if (contenedor.getVolumen() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El volumen debe ser mayor a cero.");
        }

        if (contenedor.getCliente() == null || contenedor.getCliente().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe especificarse un cliente válido.");
        }

        if (contenedor.getEstado() == null || contenedor.getEstado().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe especificarse un estado válido.");
        }
    }
}
