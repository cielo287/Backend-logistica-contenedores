package utn.frc.backend.tpi.logistica.entidades;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class TramoRuta {
    private int orden;
    private String tipoTramo;
    private LocalDate fechaEstimadaSalida;
    private LocalDate fechaEstimadaLlegada;
    private LocalDate fechaRealSalida;
    private LocalDate fechaRealLlegada;
    /*
     * private Ciudad ciudadOrigen;
    private Ciudad ciudadDestino;
     */
    private Solicitud solicitud;
}
