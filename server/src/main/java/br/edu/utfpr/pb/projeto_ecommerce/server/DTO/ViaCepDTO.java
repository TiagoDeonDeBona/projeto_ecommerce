package br.edu.utfpr.pb.projeto_ecommerce.server.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
// faz com que o que retornar da API que nao estiver declarado como atributo seja ignorado

public class ViaCepDTO {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String estado;
    private String regiao;
    private String ddd;
    private Boolean erro; // precisamos porque o ViaCEP pode retornar erro: true em casos de nao existir o CEP
}
