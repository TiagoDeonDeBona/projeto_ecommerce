package br.edu.utfpr.pb.projeto_ecommerce.server.DTO;

import br.edu.utfpr.pb.projeto_ecommerce.server.model.Address;
import br.edu.utfpr.pb.projeto_ecommerce.server.model.ItemPedido;
import br.edu.utfpr.pb.projeto_ecommerce.server.model.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoDTO {

    private Long id;
    private Long addressId;

    @NotBlank
    @Size(min = 3, max = 20)
    private String formaPagamento;

    private BigDecimal valorTotal;
    private LocalDateTime dataPedido;

    @NotEmpty // exige um item para ter o pedido
    @Valid // valida os objetos que estao dentro da lista
    private List<ItemPedidoDTO> itemPedido = new ArrayList<>();
}
