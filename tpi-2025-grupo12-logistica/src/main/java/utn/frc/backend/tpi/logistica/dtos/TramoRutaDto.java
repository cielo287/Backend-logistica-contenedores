package utn.frc.backend.tpi.logistica.dtos;

import java.sql.Date;

import lombok.Data;

@Data
public class TramoRutaDto {
    private Long id;
    private Long origenId;
    private String origenTipo; // "CIUDAD" o "DEPOSITO"
    private Long destinoId;
    private String destinoTipo; // "CIUDAD" o "DEPOSITO"
    private Date fechaEstimadaSalida;
    private Date fechaRealSalida;
    private Date fechaEstimadaLlegada;
    private Date fechaRealLlegada;
    private Double distancia;
    private Double tiempoEstimado;
}
