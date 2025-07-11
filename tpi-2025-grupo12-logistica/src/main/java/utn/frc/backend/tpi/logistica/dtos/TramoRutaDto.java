package utn.frc.backend.tpi.logistica.dtos;

import lombok.Data;

@Data
public class TramoRutaDto {
    private Long id;
    private Long origenId;
    private String origenTipo; // "CIUDAD" o "DEPOSITO"
    private Long destinoId;
    private String destinoTipo; // "CIUDAD" o "DEPOSITO"
    private Double distancia;
    private Double tiempoEstimado;
}
