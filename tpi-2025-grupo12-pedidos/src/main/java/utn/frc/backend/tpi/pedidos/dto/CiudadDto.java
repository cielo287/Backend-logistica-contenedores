package utn.frc.backend.tpi.pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CiudadDto {
    private Long id;
    private String nombre;
    private Double lat;
    private Double lon;
}
