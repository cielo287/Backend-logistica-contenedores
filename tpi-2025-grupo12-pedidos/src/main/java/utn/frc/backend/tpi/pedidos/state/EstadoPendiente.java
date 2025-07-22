package utn.frc.backend.tpi.pedidos.state;

import utn.frc.backend.tpi.pedidos.models.Contenedor;
import java.util.Set;

public class EstadoPendiente implements EstadoContenedor{
private static final Set<String> transicionesValidas = Set.of("Retirado de origen", "Retirado de depósito");

    @Override
    public String getNombre() {
        return "Pendiente de despacho";
    }

    @Override
    public boolean puedeTransicionarA(String nuevoEstado, boolean tieneDeposito) {
        if ((nuevoEstado.equals("Retirado de depósito") || nuevoEstado.equals("Entregado en depósito")) && !tieneDeposito) {
            return false;
        }
        return transicionesValidas.contains(nuevoEstado);
    }

    @Override
    public void ejecutarAccion(Contenedor contenedor) {}

    @Override
    public boolean puedeAplicarse(String nuevoEstado, boolean tieneDeposito) {
        return true;
    }
}
