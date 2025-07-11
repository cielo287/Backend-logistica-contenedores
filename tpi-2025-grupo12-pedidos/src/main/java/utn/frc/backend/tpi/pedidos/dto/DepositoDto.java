package utn.frc.backend.tpi.pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositoDto {
    private Long id;
    private Long ciudadId;
    private String direccion;
    private Double lat;
    private Double lon;
}   