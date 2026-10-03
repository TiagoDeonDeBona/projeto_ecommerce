package br.edu.utfpr.pb.projeto_ecommerce.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity // a anotação mais importante aqui determina que essa classe
// nao vai ser uma simples classe e sim pode ser persistida no banco
@Table(name = "tb_user") //na tabela tb_user
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// Implementando UserDetails estamos dizendo para o spring
// que objeto User corresponde a um usuario que o Spring Security sabe validar
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

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


    @Override
    @Transient
    @JsonIgnore
    // Aqui o spring vai estar atribuindo a todos os usuarios a autoridade de ROLE_USER
    // Futuramente caso existir mais rotas e eu estiver autenticado como usuario e quiser acessar ela
    // So vou conseguir caso a rota permitir ROLE_USER
    // Resumind o Collection<? extends GrantedAuthority>, é a forma de o Spring Security
    // Armazenar essas permissões
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }

    @Override
    @Transient // transient ta dizendo que esses dados nao vao ser persistindo no banco
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    } // define se a conta esta expirada ou nao

    @Override
    @Transient // transient ta dizendo que esses dados nao vao ser persistindo no banco
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    } // define se a conta esta bloqueada ou nao

    @Override
    @Transient // transient ta dizendo que esses dados nao vao ser persistindo no banco
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    } // as credenciais NÃO estao expiradas?
    // utilizado em casos que a senha possui validade de tempo

    @Override
    @Transient // transient ta dizendo que esses dados nao vao ser persistindo no banco
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    } // esse usuario esta habilitado?
}
