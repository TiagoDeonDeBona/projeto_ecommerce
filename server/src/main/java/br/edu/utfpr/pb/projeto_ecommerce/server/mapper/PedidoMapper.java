package br.edu.utfpr.pb.projeto_ecommerce.server.mapper;

import br.edu.utfpr.pb.projeto_ecommerce.server.DTO.PedidoDTO;
import br.edu.utfpr.pb.projeto_ecommerce.server.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ItemPedidoMapper.class)
// Significa que o PedidoMapper pode usar o ItemPedidoMapper
// Exemplo meu pedido tem 3 itemPedido, esses itemPedido tambem precisam ser convertidos por isso colocamos que
// ele vai usar o itemPedidoMapper
public interface PedidoMapper {

    //como estamos do banco para trnasformar em DTO no banco temos o Product ja no DTO temos que transformar em id
    //entao fazemos dessa forma
    @Mapping(target = "addressId", source = "address.id")
    PedidoDTO toDTO(Pedido pedido);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "valorTotal", ignore = true)
    @Mapping(target = "dataPedido", ignore = true)
    @Mapping(target = "itemPedido", ignore = true)
    Pedido toEntity(PedidoDTO dto);
    // ignoramos alguns elementos porque as model estao esperando chegar a classe inteira
    // mas estamos passando somente o id dessas classes o mapeamento dos id vai ser no service

}
