package br.edu.utfpr.pb.projeto_ecommerce.server.security;

import br.edu.utfpr.pb.projeto_ecommerce.server.DTO.AuthRequestDTO;
import br.edu.utfpr.pb.projeto_ecommerce.server.model.User;
import br.edu.utfpr.pb.projeto_ecommerce.server.security.dto.AuthenticationResponse;
import br.edu.utfpr.pb.projeto_ecommerce.server.security.dto.UserResponseDTO;
import br.edu.utfpr.pb.projeto_ecommerce.server.service.AuthService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import tools.jackson.core.exc.StreamReadException;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;


@NoArgsConstructor
// esse extends é uma classe que ja existe no Spring Security que é baseada em Username + password
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private AuthService authService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   AuthService authService) {
        this.authenticationManager = authenticationManager; // esse cara aqui foi criado la dentro do
        // web security que é o que verifica a autenticacao
        this.authService = authService;
    }

    // metodo chamado quando corre tentativa de conexão como o nome ja diz
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                @NonNull HttpServletResponse response)
            throws AuthenticationException {

        try {

            AuthRequestDTO credentials = new AuthRequestDTO();
            // Cria classe que vai receber o objeto da requisição JSON

            User user = new User();

            //request.getInputStream pega o corpo da requisição e verifica se tem algo para ser lido
            if (request.getInputStream() != null || request.getInputStream().available() > 0) {
                // pega o objeto JSON e com o MAPPER transforma a requisição json (request.getInputStream)
                // em um objeto java AuthRequestDTO.
                // depois procura o usuario, no banco atravez do AuthService pelo nome do usuario que veio
                // do JSON
                credentials = new ObjectMapper().readValue(request.getInputStream(), AuthRequestDTO.class);
                user = (User) authService.loadUserByUsername(credentials.getUsername());
            }
            //Caso o usuário seja encontrado, o objeto authenticationManager encarrega-se de autenticá-lo.
            //Como o authenticationManager foi configurado na classe WebSecurity e, foi informado o método
            //de criptografia da senha, a senha informada durante a autenticação é criptografada e
            //comparada com a senha armazenada no banco. Caso não esteja correta uma Exception será disparada
            //Caso ocorra sucesso será chamado o método: successfulAuthentication dessa classe
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken( // vamos cria um objeto que
                            credentials.getUsername(), // tem o nome da requisição
                            credentials.getPassword(), // a senha que vamos criptografar com o authneticationManager
                            user.getAuthorities() // e as permissão desse usuario
                    )
            ); // entregando para o authenticationManager que sabe aonde conferir os usuarios do banco
            // e como criptografar a senha para comparar com o hash do banco

        } catch (StreamReadException | DatabindException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    // caso dar tudo certo
    // authResult  vai ter o objeto que passou da validação anterior
    @Override
    protected void successfulAuthentication(@NonNull HttpServletRequest request, // vamos ter a request
                                            HttpServletResponse response, // a resposta
                                            @NonNull FilterChain chain, // o bloco de seguranca
                                            Authentication authResult) throws IOException, ServletException { // e objeto q passou

        User user = (User) authService.loadUserByUsername(authResult.getName());
        //vamos buscar o user completo no banco com todas informações
        // o método create() da classe JWT é utilizado para criação de um novo token JWT
        String token = JWT.create()
                // o objeto authResult possui os dados do usuário autenticado, nesse caso o método getName() retorna o username do usuário foi autenticado no método attemptAuthentication.
                .withSubject(authResult.getName()) // o nome é colocado dentro do Token
                //a data de validade do token é a data atual mais o valor armazenado na constante EXPIRATION_TIME, nesse caso 1 dia
                .withExpiresAt( // coloca para expirar pegando a data atual + a variavel criada em SecurityConstants
                        new Date(System.currentTimeMillis()  + SecurityConstants.EXPIRATION_TIME)
                )
                //Por fim é informado o algoritmo utilizado para assinar o token e por parâmetro a chave utilizada para assinatura.
                // O Secret também pode ser alterado na classe SecurityConstants que armazena alguns dados de configuração do Spring Security
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET));

        response.setContentType("application/json"); // a resposta que eu vou devolver é um json

        // ele vai criar o UserResponseDTO que vai ter o email, username e as permissoes do malandro
        // depois ele cria o Token de resposta, que vai ser o objeto UserResponse + TOKEN JWT pronto
        // depois com o mpaper transforma denovo em um JSON
        // e a resposta vai ser o JSON finalizado
        response.getWriter().write(
                new ObjectMapper().writeValueAsString(
                        new AuthenticationResponse(token, new UserResponseDTO(user)))
        );

    }

    @Override
    public AuthenticationSuccessHandler getSuccessHandler() {
        return super.getSuccessHandler(); // mantem o comportamento da classe pai
        // UsernamePasswordAuthenticationFilter
    }
}