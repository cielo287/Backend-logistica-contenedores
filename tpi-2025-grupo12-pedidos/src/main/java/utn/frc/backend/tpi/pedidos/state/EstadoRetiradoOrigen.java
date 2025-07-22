package utn.frc.backend.tpi.pedidos.state;
import java.util.Set;
import utn.frc.backend.tpi.pedidos.models.Contenedor;  

public class EstadoRetiradoOrigen implements EstadoContenedor{
    private static final Set<String> transicionesValidas = Set.of("Entregado en destino", "Entregado en depósito");

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

    @Override
    public boolean puedeAplicarse(String nuevoEstado, boolean tieneDeposito) {
        return true;
    }

}
