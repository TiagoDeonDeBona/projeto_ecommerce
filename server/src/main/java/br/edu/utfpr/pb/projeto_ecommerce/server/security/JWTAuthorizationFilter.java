package br.edu.utfpr.pb.projeto_ecommerce.server.security;

import br.edu.utfpr.pb.projeto_ecommerce.server.model.User;
import br.edu.utfpr.pb.projeto_ecommerce.server.service.AuthService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

// estamos dizendo que essa classe vai aproveitar o comportamento de filtro
// que ja existe no Spring Security
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final AuthService authService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                  AuthService authService) {
        // Envia o AuthenticationManager para o construtor
        // da classe pai BasicAuthenticationFilter.
        super(authenticationManager);
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, // requisicao que chegou
                                    HttpServletResponse response, // resposta que podera ser enviada
                                    FilterChain chain) throws IOException, ServletException {

        // ele vai procurar o JWT, indo nos header procurando o HEADER_STRING = "Authorization";
        // depois achando o header ele pega o JWT
        String header = request.getHeader(SecurityConstants.HEADER_STRING);

        //Verifica se o token existe no cabeçalho
        // primeiro verifica se o token existe depois compara se o header que chegou possui "Bearer "
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response); // aqui vai derrubar caso nao tiver autenticado
            return;
        }

        //agora vou pegar o token que veio da requisição e descobrir qual é o malandro que ta conectado
        UsernamePasswordAuthenticationToken authenticationToken =
                getAuthentication(request);

        //Adiciona o usuário autenticado no contexto do spring security
        // vamos usar depois para pegar id do usuario para colocar no pedido
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        // pega o header novamente o Bearer Token

        //verifica se o token é válido e retorna o username
        String username = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET)) // valida o jwt
                .build()
                .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                .getSubject();

        if (username != null) {
            // com posse do username é verificado se ele existe na base de dados
            User user = (User) authService.loadUserByUsername(username);
            //caso exista o usuário é autenticado e a requisição continua a ser executada.
            return new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    null,
                    user.getAuthorities());
        }
        // senão é retornado null, se a url que o usuário solicitou necessita de autenticação ele vai receber erro 401 - Unauthorized
        return null;
    }
}