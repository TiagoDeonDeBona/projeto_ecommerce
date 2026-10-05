package br.edu.utfpr.pb.projeto_ecommerce.server.DTO;

import lombok.Data;

// a função dessa clase é representar o JSON que chega quando fazemos o /LOGIN
//{
//        "username": "tiago",
//        "password": "Senha123"
//}
// conforme o restante do DTO a funcionalidade dela é so transportar os dados necessarios
// nesse caso o usuario e a senha para logar o restante nao precisa.
@Data
public class AuthRequestDTO {
    private String username;
    private String password;
}
