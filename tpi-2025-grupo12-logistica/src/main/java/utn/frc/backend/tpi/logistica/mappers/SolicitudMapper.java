package utn.frc.backend.tpi.logistica.mappers;

import org.mapstruct.Mapper;

import utn.frc.backend.tpi.logistica.dtos.SolicitudDto;
import utn.frc.backend.tpi.logistica.models.Solicitud;

@Mapper(componentModel = "spring", uses = TramoRutaMapper.class)
public interface SolicitudMapper {
    SolicitudDto toDto(Solicitud solicitud);
    Solicitud toEntity(SolicitudDto dto);

}
