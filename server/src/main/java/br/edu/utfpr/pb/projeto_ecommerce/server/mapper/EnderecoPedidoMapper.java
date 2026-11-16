package br.edu.utfpr.pb.projeto_ecommerce.server.mapper;

import br.edu.utfpr.pb.projeto_ecommerce.server.DTO.EnderecoPedidoDTO;
import br.edu.utfpr.pb.projeto_ecommerce.server.model.Address;
import br.edu.utfpr.pb.projeto_ecommerce.server.model.EnderecoPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EnderecoPedidoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enderecoOriginalId", source = "id") // pegue o endereco do objeto que eu passei e use nesse campo
    EnderecoPedido toEntity(Address address);

    EnderecoPedidoDTO toDTO(EnderecoPedido enderecoPedido);
}
