package utn.frc.backend.tpi.pedidos.mapper;

import org.mapstruct.Mapper;

import utn.frc.backend.tpi.pedidos.dto.EstadoDTO;
import utn.frc.backend.tpi.pedidos.models.Estado;

@Mapper(componentModel = "spring")
public interface EstadoMapper {
    EstadoDTO toDTO(Estado estado);
    Estado toEntity(EstadoDTO dto);

} 