package br.edu.utfpr.pb.projeto_ecommerce.server.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_enderecoPedido")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EnderecoPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long enderecoOriginalId;
    private String cep;
    private String rua;
    private String numero;
    private String complemento;
    private String estado;
    private String cidade;
    private String regiao;
    private String uf;
    private String ddd;
    private String nome_recebimento;
    private String telefone_contato;
}
