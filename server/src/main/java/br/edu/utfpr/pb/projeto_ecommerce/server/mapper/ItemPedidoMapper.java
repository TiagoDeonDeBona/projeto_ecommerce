package br.edu.utfpr.pb.projeto_ecommerce.server.mapper;

import br.edu.utfpr.pb.projeto_ecommerce.server.DTO.ItemPedidoDTO;
import br.edu.utfpr.pb.projeto_ecommerce.server.model.ItemPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemPedidoMapper {
    //como estamos do banco para trnasformar em DTO no banco temos o Product ja no DTO temos que transformar em id
    //entao fazemos dessa forma
    @Mapping(target = "productId", source = "product.id")
    ItemPedidoDTO toDTO(ItemPedido entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "precoUnitario", ignore = true)
    ItemPedido toEntity(ItemPedidoDTO dto);
    // ignoramos alguns elementos porque as model estao esperando chegar a classe inteira
    // mas estamos passando somente o id dessas classes o mapeamento dos id vai ser no service
}
