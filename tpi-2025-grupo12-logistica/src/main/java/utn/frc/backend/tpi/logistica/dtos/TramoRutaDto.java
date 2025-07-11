package utn.frc.backend.tpi.logistica.dtos;

import lombok.Data;

@Data
public class TramoRutaDto {
    private Long id;
    private Long origen;
    private Long destino;
    private Double distancia;
    private Double tiempoEstimado;

}
