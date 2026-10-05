package br.edu.utfpr.pb.projeto_ecommerce.server.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// esse cara vai ser a resposta do logim bem sucedido
// o usuario com o token
public class AuthenticationResponse {
    private String token;
    private UserResponseDTO user;
}

