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
    
    public ContenedorDTO(Contenedor contenedor) {
        this.id = contenedor.getId();
        this.peso = contenedor.getPeso();
        this.volumen = contenedor.getVolumen();
    }

}
