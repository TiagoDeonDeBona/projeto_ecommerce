package br.edu.utfpr.pb.projeto_ecommerce.server.DTO;

import br.edu.utfpr.pb.projeto_ecommerce.server.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
    private Long id;

    @NotBlank
    @Size(min = 8, max = 8)
    @Pattern(regexp = "^\\d+$") // aceitar so numeros
    private String cep;

    @NotBlank
    @Size(min = 2, max = 70)
    private String rua;

    @NotBlank
    @Size(min = 1, max = 5)
    private String numero;

    @NotBlank
    @Size(min = 1, max = 70)
    private String complemento;

    @NotBlank
    @Size(min = 4, max = 25)
    private String estado;

    @NotBlank
    @Size(min = 3, max = 40)
    private String cidade;

    @NotBlank
    @Size(min = 3, max = 20)
    private String regiao;

    @NotBlank
    @Size(min = 2, max = 2)
    private String uf;

    @NotBlank
    @Size(min = 2, max = 3)
    private String ddd;

    @NotBlank
    @Size(max = 70)
    private String nome_recebimento;

    @NotBlank
    @Size(max = 20)
    private String telefone_contato;

    private UserDTO user;
}
