package br.edu.utfpr.pb.projeto_ecommerce.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity // a anotação mais importante aqui determina que essa classe
// nao vai ser uma simples classe e sim pode ser persistida no banco
@Table(name = "tb_user") //na tabela tb_user
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    @Size(min = 4, max = 50) // tamanho min e maximo do body que vamos receber
    @Column(length = 50) // indica para o hibernate que a coluna do banco vai ser varchar(50)
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
