package br.edu.utfpr.pb.projeto_ecommerce.server.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
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
public class UserDTO {

    @NotBlank
    @Size(min = 4, max = 50) // tamanho min e maximo do body que vamos receber
    private String username;

    @NotBlank // nao permite espaços vazios, nulos ou em branco
    @Email // e aceita apenas em formato de email
    private String email;

    @NotBlank
    @Size(min = 6)
    // A expressão regular da anotação @Pattern valida para que o atributo tenha pelo menos 1 letra maiúscula, 1 letra minúscula e 1 número.
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
    private String password;
}
