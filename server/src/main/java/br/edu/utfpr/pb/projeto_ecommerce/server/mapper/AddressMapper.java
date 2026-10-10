package br.edu.utfpr.pb.projeto_ecommerce.server.mapper;

import br.edu.utfpr.pb.projeto_ecommerce.server.DTO.AddressDTO;
import br.edu.utfpr.pb.projeto_ecommerce.server.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = UserMapper.class)
public interface AddressMapper {
    Address toEntity(AddressDTO dto);
    AddressDTO toDTO(Address entity);
}
