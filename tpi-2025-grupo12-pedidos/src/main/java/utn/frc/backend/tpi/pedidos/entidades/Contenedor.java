package utn.frc.backend.tpi.pedidos.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contenedor {
    private double peso;
    private double volumen;
    private Estado estado;
}
