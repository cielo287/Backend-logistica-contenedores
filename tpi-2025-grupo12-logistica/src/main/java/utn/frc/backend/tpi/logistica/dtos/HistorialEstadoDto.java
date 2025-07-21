package utn.frc.backend.tpi.logistica.dtos;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistorialEstadoDto {
    private Long contenedorId;
    private Long estadoId;
    private LocalDate fechaCambio;    

}
