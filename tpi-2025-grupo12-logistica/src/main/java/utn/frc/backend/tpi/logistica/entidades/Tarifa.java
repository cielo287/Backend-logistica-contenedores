package utn.frc.backend.tpi.logistica.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tarifa {
    private double montoBase;
    private double costoPorKm;
    private double costoPorDia;
}
