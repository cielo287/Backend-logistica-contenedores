package utn.frc.backend.tpi.logistica.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TramoRuta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;

    @Column(name = "tipo_tramo")
    private String tipo;

    @Column(name = "ciudad_origen_id")
    private Long ciudadOrigenId;

    @Column(name = "ciudad_destino_id")
    private Long ciudadDestinoId;

    @Column(name = "orden")
    private int orden;

    @Column(name = "distancia")
    private Double distancia;

    @Column(name = "tiempo_estimado")
    private Double tiempoEstimado;

    @Column(name = "fecha_estimada_salida")
    private LocalDate fechaEstimadaSalida;

    @Column(name = "fecha_estimada_llegada")
    private LocalDate fechaEstimadaLlegada;

    @Column(name = "fecha_real_salida")
    private LocalDate fechaRealSalida;

    @Column(name = "fecha_real_llegada")
    private LocalDate fechaRealLlegada;

}
