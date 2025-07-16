package utn.frc.backend.tpi.pedidos.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EstadoSimpleDto {
    private String nombreEstado;
    private LocalDateTime fechaCambio;
}
