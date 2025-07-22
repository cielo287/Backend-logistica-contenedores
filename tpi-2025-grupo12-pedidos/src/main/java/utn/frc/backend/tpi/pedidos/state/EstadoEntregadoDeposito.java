package utn.frc.backend.tpi.pedidos.state;
import java.util.Set;
import utn.frc.backend.tpi.pedidos.models.Contenedor;

public class EstadoEntregadoDeposito implements EstadoContenedor {
    private static final Set<String> transicionesValidas = Set.of("Retirado de depósito");
    
    @Override
    public String getNombre() {
        return "Entregado en depósito";
    }

    @Override
    public boolean puedeTransicionarA(String nuevoEstado) {
        return transicionesValidas.contains(nuevoEstado);
    }

    @Override
    public void ejecutarAccion(Contenedor contenedor) {
        // Opcional: notificar al sistema de inventario
    }

    @Override
    public boolean puedeAplicarse(String nuevoEstado, boolean tieneDeposito) {
        return tieneDeposito || !nuevoEstado.equals("Entregado en depósito");
    }


}
