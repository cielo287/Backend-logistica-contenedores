package utn.frc.backend.tpi.pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.frc.backend.tpi.pedidos.models.Contenedor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContenedorDTO {

    private Long id;
    private double peso;
    private double volumen;
    private Long estadoId;

    public ContenedorDTO(Contenedor contenedor) {
        this.id = contenedor.getId();
        this.peso = contenedor.getPeso();
        this.volumen = contenedor.getVolumen();
        this.estadoId = contenedor.getEstado() != null ? contenedor.getEstado().getId() : null;
    }
}