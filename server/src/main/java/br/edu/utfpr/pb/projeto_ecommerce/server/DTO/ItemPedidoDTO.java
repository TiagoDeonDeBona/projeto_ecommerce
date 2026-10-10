package br.edu.utfpr.pb.projeto_ecommerce.server.DTO;

import br.edu.utfpr.pb.projeto_ecommerce.server.model.Pedido;
import br.edu.utfpr.pb.projeto_ecommerce.server.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedidoDTO {

    private Long id;
    private Long productId;

    @NotNull
    @Positive
    private Integer quantidade;

}
