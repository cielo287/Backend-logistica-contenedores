package utn.frc.backend.tpi.pedidos.entidades;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Deposito {
    private Ciudad ciudad;
    private String direccion;
    private double latitud;
    private double longitud;
}
