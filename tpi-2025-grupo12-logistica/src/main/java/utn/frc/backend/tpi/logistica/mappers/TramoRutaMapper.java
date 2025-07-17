package utn.frc.backend.tpi.logistica.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import utn.frc.backend.tpi.logistica.dtos.TramoRutaDto;
import utn.frc.backend.tpi.logistica.models.TramoRuta;

@Mapper(componentModel = "spring")
public interface TramoRutaMapper {
    TramoRutaDto toDTO(TramoRuta tramoRuta);
    List<TramoRutaDto> toDtoList(List<TramoRuta> tramos);

    TramoRuta toEntity(TramoRutaDto dto);
    List<TramoRuta> toEntityList(List<TramoRutaDto> dtoList);
}
