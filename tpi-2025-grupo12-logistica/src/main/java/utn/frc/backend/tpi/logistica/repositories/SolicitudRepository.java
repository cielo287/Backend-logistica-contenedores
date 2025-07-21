package utn.frc.backend.tpi.logistica.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import utn.frc.backend.tpi.logistica.models.Solicitud;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

@Query("SELECT s FROM Solicitud s WHERE s.contenedorId = :contenedorId")
Optional<Solicitud> findByContenedorId(@Param("contenedorId") Long contenedorId);


}
