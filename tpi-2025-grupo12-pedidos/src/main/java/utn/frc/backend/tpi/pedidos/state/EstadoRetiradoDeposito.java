package utn.frc.backend.tpi.pedidos.state;

import java.util.Set;
import utn.frc.backend.tpi.pedidos.models.Contenedor;

public class EstadoRetiradoDeposito implements EstadoContenedor{
    private static final Set<String> transicionesValidas = Set.of("Entregado en destino", "Entregado en depósito");

    @Override
    public String getNombre() {
        return "Retirado de depósito";
    }

    @Override
    public boolean puedeTransicionarA(String nuevoEstado) {
        return transicionesValidas.contains(nuevoEstado);
    }

    @Override
    public void ejecutarAccion(Contenedor contenedor) {
        // Lógica personalizada si se quiere
    }

    @Override
    public boolean puedeAplicarse(String nuevoEstado, boolean tieneDeposito) {
        return tieneDeposito || !nuevoEstado.equals("Retirado de depósito");
    }

}
