package br.edu.utfpr.pb.projeto_ecommerce.server.controller;

import br.edu.utfpr.pb.projeto_ecommerce.server.model.User;
import br.edu.utfpr.pb.projeto_ecommerce.server.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController // fala para o Spring que essa classe vai receber e tratar requisições http
// a requisição vem de fora pra dentro e nao da classe pra fora
@RequestMapping("users") // siginifica que todos os metodos teram de base o /users
public class UserController {
    private final UserService userService; // ela vai usar como atributo um elemento service

    public UserController(UserService userService){ // declara no construtor porque ele vai usar
        this.userService = userService;
    }

    @PostMapping // esse metodo tem que ser executado quando receber um post.
    @ResponseStatus(HttpStatus.CREATED) // retorna a validacao do created que o usuario precisa
    void createUser(@RequestBody User user){ // aqui é @RequestBody porque estamos dizendo para classe daonde vai
        // vir o objeto, nesse caso do corpo da requisição ou seja o JSON com os dados que vamos transformar em objeto User
        this.userService.save(user);
        // aqui ele ta chamando a funcao que ele usa do userRepository porem futuramente
        // ele vai chamar o userRepository para salvar separadamente aqui
    }
}
