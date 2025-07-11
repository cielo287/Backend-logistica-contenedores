package utn.frc.backend.tpi.logistica.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.frc.backend.tpi.logistica.interfaces.Ubicable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositoDto implements Ubicable {
    private Long id;
    private Long ciudadId;
    private String direccion;
    private Double lat;
    private Double lon;

    @Override
    public String getTipo() {
        return "DEPOSITO";
    }
}