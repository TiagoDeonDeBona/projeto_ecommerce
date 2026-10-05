package br.edu.utfpr.pb.projeto_ecommerce.server.security;

import br.edu.utfpr.pb.projeto_ecommerce.server.service.AuthService;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableWebSecurity
@Configuration // indica para o spring que essa classe é de configuração
public class WebSecurity {

    private final AuthService authService;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public WebSecurity(AuthService authService, AuthenticationEntryPoint authenticationEntryPoint){
        this.authService = authService;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }


    @Bean // siginifica que o spring sera responsavel por manipular | criar o objeto quando solicitado
    @SneakyThrows // serve meio que para diblar o serviço de ter que fazer throws Exception nas funções ou o famoso
    // try catch, ele diz, se dentro do filterChain qualquer metodo lançar uma exceção verificada para o java, retorne
    // sem eu precisar fazer o tratamento
    public SecurityFilterChain filterChain(HttpSecurity http) { // cadeira de filtro de segurança

        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        // aqui eu estou pegando o HttpSecurity que é o nosso molde e solicitando
        // para pegar oque está sendo usado nessa configuração de segurança

        authenticationManagerBuilder
                .userDetailsService(authService) // mostro aonde pegar o usuario
                // que é dentro do AuthService que vai ter o loadUserByUsername -> UserRepository -> Banco
                // o spring espera o UserDetailsService e nos entregamos o AuthService que
                // implementa o contrato do UserDetailsService
                .passwordEncoder(passwordEncoder()); // mostro como lidar com a senha
                // ou seja o service guarda senha criptografada com BCrypt e eu uso
                // eu pego a senha que veio da requisição criptografo e confiro com o HASH criado no banco

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        // ele pega e junta os dois, pega o padrão que tava de autenticar o usuario
        // e altera para fazer conforme o .userDetailsService(authService) e .passwordEncoder() mandarem

        /*
        AuthService
        "eu sei encontrar usuário"

           ↓

        WebSecurity
        "então você será o serviço que
        meu AuthenticationManager vai usar"

           ↓

        AuthenticationManager
        "quando chegar login, já sei para
        quem pedir o usuário"
        */

        //Configuração para funcionar o console do H2.
        http.headers( // aqui o headers() diz quero conferir alguns cabecalhos de seguranca
                     // que o spring envia nas requisicoes HTTP
                headers -> headers // objeto de seguranca que vou receber
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                        // frameOptions() -> dentro dos header eu quero configurar a opção relacionada a frames
        );              // HeadersConfigurer.FrameOptionsConfig::disable -> e eu quero desativar essa protecao que a pagina nao
                        // possa ser reproduzida em frame

        http.csrf(AbstractHttpConfigurer::disable);

        // aqui ele ta dizendo para o Spring habilite as configuraçÕes que eu defini
        // na função la embaixo em corsConfigurationSource();
        http.cors(cors -> corsConfigurationSource());

        // Aqui é serve como tratamento de Erros, como antes criamos o EntryPointUnauthorizedHandler e implementamos
        // o contrato do AuthenticationEntryPoint, ajustamos a função commence(), aqui nos cahamos denovo o
        // AuthenticationEntryPoint ajustado para retornar quando ocorrer um erro
        // retornando
//      //response.setStatus(HttpStatus.UNAUTHORIZED.value()); // aqui estamos falando que vai retornar o erro 401
//      //response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        http.exceptionHandling(
                exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(authenticationEntryPoint)
        );

        // o (authorize) é sintaxe, diz o objeto que eu vou configurar vai se chamar
        // (authorize) -> authorize.requestMatchers...

        http.authorizeHttpRequests((authorize) -> authorize
                // qualquer requisição Post para /users pode ser feita sem autenticação
                .requestMatchers(HttpMethod.POST, "/users/**").permitAll()
                //quando nao passado o HttpMethod estamos dizendo que todos os metodos podem
                // quando passado tipo o /users se eu tentar fazer um GET /USERS nao vou conseguir
                .requestMatchers("/error/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/products/**").permitAll()
                .requestMatchers("/categories/**").permitAll()
                // Aqui para todas as rotas.
                // o ** significa para todos niveis abaixos exemplo
                // /users /users/tiago

                .anyRequest().authenticated()
                // aqui esta dizendo que qualquer requisição que nao tenha sido liberada pelas regras anteriores
                // exigem que o usuario esteja autenticado para acessar
        );

        http.authenticationManager(authenticationManager) // define quem gerencia a autenticacao
                // JWTAuthenticationFilter vai ser responsavel por cuidar do login
                // adiciona o filtro responsavel pelo login
                // recebe username e password, usa o AuthenticationManager que criamos la encima
                // que sabe aonde pega o usuario e a senha criptografada para comparar
                // se a autenticação da certo ele gera o JWT
                .addFilter(new JWTAuthenticationFilter(authenticationManager, authService))
                //Cuida da validação do token nas proximas requisições
                // adiciona o filtro responsaveis depois do login ou seja, vai ler nas requisiçÕes
                // quem esta tentando acessar valida o token e identifica qual usario ta tentando acessar
                .addFilter(new JWTAuthorizationFilter(authenticationManager, authService))
                //Stateless diz que o servidor nao mantera sessao portanto o JWT deve acompanhar cada requisição protegida
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
        // retorna o objeto de segurança que vamos aplicar
    }

    // porque caralhos eu crio ele fora e nao antes?
    @Bean // se nao me engano o Bean serve para salvar algo local durante execução
    // permitindo que o spring utilize durante a execução
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Lista das origens autorizadas, no nosso caso que iremos rodar a aplicação localmente o * poderia ser trocado
        // por: http://localhost:porta, em que :porta será a porta em que a aplicação cliente será executada
        configuration.setAllowedOrigins(List.of("*")); //aceita requisição de qualquer origem
        // Lista dos métodos HTTP autorizados das portas permitidas
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT"));
        // Lista dos Headers autorizados, o Authorization será o header que iremos utilizar para transferir o Token
        configuration.setAllowedHeaders(List.of("Authorization","x-xsrf-token",
                "Access-Control-Allow-Headers", "Origin",
                "Accept", "X-Requested-With", "Content-Type",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers", "Auth-Id-Token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // configura quais rotas vao
        // receber as configurações acima
        source.registerCorsConfiguration("/**", configuration); // define que todas rotas vao obedecer a requisição acima
        // retorna a configuração do cor
        return source;
    }
}
