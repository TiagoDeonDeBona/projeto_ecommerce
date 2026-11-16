package br.edu.utfpr.pb.projeto_ecommerce.server.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnderecoPedidoDTO {
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
