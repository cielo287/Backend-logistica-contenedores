package utn.frc.backend.tpi.pedidos.mapper;

import org.mapstruct.Mapper;

import utn.frc.backend.tpi.pedidos.dto.ClienteDTO;
import utn.frc.backend.tpi.pedidos.models.Cliente;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    ClienteDTO toDTO(Cliente cliente);
    Cliente toEntity(ClienteDTO dto);


}
