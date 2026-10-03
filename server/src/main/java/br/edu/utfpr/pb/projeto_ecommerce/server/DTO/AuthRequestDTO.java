package br.edu.utfpr.pb.projeto_ecommerce.server.DTO;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String username;
    private String password;
}
