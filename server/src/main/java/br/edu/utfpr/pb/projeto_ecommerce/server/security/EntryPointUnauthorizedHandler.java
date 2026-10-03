package br.edu.utfpr.pb.projeto_ecommerce.server.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

// Diz para o spring crie e gerencie um objeto com esse nome
@Component("authenticationEntryPoint")
public class EntryPointUnauthorizedHandler // mesma coisa do repository com o service
        implements AuthenticationEntryPoint { // estamos fazendo o EntryPoint usar funções da interface pronta

    @Override //HttpServletRequest request é a requisição que vamos receber
    public void commence(@NonNull HttpServletRequest request, //
                         HttpServletResponse response, // a resposta que vamos enviar
                         @NonNull AuthenticationException authException) // verifica qual erro de autenticação ocorreu
                            throws IOException, ServletException { // erros genericos para algumas ocasioes
        // da mesma forma que quando da certo HttpStatus.CREATED definimos quando da erro
        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // aqui estamos falando que vai retornar o erro 401
        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        // e a mensagem de erro vai possuir o codigo (401 = HttpStatus.UNAUTHORIZED.value())
                                // e a frase do erro (Unauthorized = HttpStatus.UNAUTHORIZED.getReasonPhrase())
    }
}
