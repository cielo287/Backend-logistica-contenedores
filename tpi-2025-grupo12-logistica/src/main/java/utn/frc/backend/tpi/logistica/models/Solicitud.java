package utn.frc.backend.tpi.logistica.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contenedor_id")
    private Long contenedorId;

    @Column(name = "ciudad_origen_id")
    private Long ciudadOrigenId;

    @Column(name = "ciudad_destino_id")
    private Long ciudadDestinoId;

    @Column(name = "deposito_id")
    private Long depositoId;

    @Column(name = "camion_id")
    private Long camionId;

    @Column(name = "costo_estimado")
    private double costoEstimado;

    @Column(name = "tiempo_estimado_horas")
    private double tiempoEstimadoHoras;
}
