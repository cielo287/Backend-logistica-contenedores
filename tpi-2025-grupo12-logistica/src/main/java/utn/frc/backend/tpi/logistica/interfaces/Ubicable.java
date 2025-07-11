package utn.frc.backend.tpi.logistica.interfaces;

public interface Ubicable {
    Long getId();

    Double getLat();

    Double getLon();

    String getTipo(); // "CIUDAD" o "DEPOSITO"
}
