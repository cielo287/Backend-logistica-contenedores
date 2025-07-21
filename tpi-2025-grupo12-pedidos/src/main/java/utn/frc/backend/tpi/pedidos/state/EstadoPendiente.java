package utn.frc.backend.tpi.pedidos.state;

import utn.frc.backend.tpi.pedidos.models.Contenedor;
import java.util.Set;

public class EstadoPendiente implements EstadoContenedor{
    private static final Set<String> transicionesValidas = Set.of("Retirado de origen", "Retirado de depósito");

    @Override
    public String getNombre(){
        return "Pendiente de despacho";
    }

    @Override
    public boolean puedeTransicionarA(String nuevoEstado){
        return transicionesValidas.contains(nuevoEstado);
    }

    @Override
    public void ejecutarAccion(Contenedor contenedor){

    }

}
