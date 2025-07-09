package utn.frc.backend.tpi.pedidos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import utn.frc.backend.tpi.pedidos.models.Contenedor;

@Repository
public interface ContenedorRepository extends JpaRepository<Contenedor,Long>{
    
}
