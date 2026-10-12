package br.edu.utfpr.pb.projeto_ecommerce.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_pedido")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column(length = 20, nullable = false)
    private String formaPagamento;

    @NotNull
    @Column(nullable = false)
    private BigDecimal valorTotal;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime dataPedido;

    @NotNull
    @Column(nullable = false)
    private BigDecimal valorFrete;

    //Mappeby =  indica qual o atributo da outra classe vai representar a ligacao
    //cascade = CascadeType.ALL quando salver o pedido eu salvo tambem os itemPedido na tabela itemPedido assim nao
    //preciso de repository para o itemPedido so salva quando finaliza pedido;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    // Um pedido pode ter muitos itens
    @Builder.Default // faz que a lista seja preservada usando o @Builder do Lombok
    private List<ItemPedido> itemPedido = new ArrayList<>();

}
