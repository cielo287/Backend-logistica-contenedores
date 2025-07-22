package utn.frc.backend.tpi.pedidos.state;

import java.util.Set;
import utn.frc.backend.tpi.pedidos.models.Contenedor;

public class EstadoEntregadoDestino implements EstadoContenedor {
    @Override
    public String getNombre() {
        return "Entregado en destino";
    }

    @Override
    public boolean puedeTransicionarA(String nuevoEstado) {
        return false; // No se puede salir de este estado
    }

    @Override
    public void ejecutarAccion(Contenedor contenedor) {
        // Cierre automático de pedido, notificar cliente, etc.
    }

    @Override
    public boolean puedeAplicarse(String nuevoEstado, boolean tieneDeposito) {
        return true;
    }

}
