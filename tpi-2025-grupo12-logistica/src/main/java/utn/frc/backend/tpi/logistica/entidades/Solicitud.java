package utn.frc.backend.tpi.logistica.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Solicitud {
    /* 
     * private Ciudad ciudadOrigen;
    private Ciudad ciudadDestino;
    private Deposito deposito;
    private Camion camion;
    */

    private double costoEstimado;
    private double tiempoestimado;
}
