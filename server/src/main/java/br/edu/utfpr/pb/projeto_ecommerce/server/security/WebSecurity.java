package br.edu.utfpr.pb.projeto_ecommerce.server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // indica para o spring que essa classe é de configuração
public class WebSecurity {

    @Bean // siginifica que o spring sera responsavel por manipular | criar o objeto quando solicitado
    public SecurityFilterChain filterChain(HttpSecurity http) { // cadeira de filtro de segurança
        http // vai receber um objeto HTTP para realizarmos configurações de segurança
                .csrf(AbstractHttpConfigurer::disable) // ISSO TAFALANDO ASSIM PRO SPRING
                // Quando estiver configurando o .csrf que é um barramento de segurnaça
                // deixa ele desabilitado .csrf(config -> config.disable())

                .authorizeHttpRequests(auth -> auth // aqui estamos dizendo quais pessoas podem acessar
                        .anyRequest().permitAll() // Todas as rotas sem necessidade de autenticação ou autorização
                ); // nesse caso o auth é objeto que vai receber as config do Http Request
        return http.build();
        // retorna o objeto de segurança que vamos aplicar
    }
}
