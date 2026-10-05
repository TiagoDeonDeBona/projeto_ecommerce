package br.edu.utfpr.pb.projeto_ecommerce.server.security.dto;

import br.edu.utfpr.pb.projeto_ecommerce.server.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// retorna somente o necessário nunca passar senha nem id
public class UserResponseDTO {
    private String email;
    private String username;
    private Set<AuthorityResponseDTO> authorities; // conjunto de permissao do usuario
    // nesse caso o authorities = "ROLE_USER"

    public UserResponseDTO(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.authorities = new HashSet<>();
        for(GrantedAuthority authority: user.getAuthorities()){
            authorities.add(new AuthorityResponseDTO(authority.getAuthority()));
        } // apenas pega todas permissoes do usuario e enviar para o AuthenticationResponse que vai ter o Token e usuario
    }
}
