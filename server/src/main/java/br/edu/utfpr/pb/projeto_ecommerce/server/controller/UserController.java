package br.edu.utfpr.pb.projeto_ecommerce.server.controller;

import br.edu.utfpr.pb.projeto_ecommerce.server.DTO.UserDTO;
import br.edu.utfpr.pb.projeto_ecommerce.server.mapper.UserMapper;
import br.edu.utfpr.pb.projeto_ecommerce.server.model.User;
import br.edu.utfpr.pb.projeto_ecommerce.server.service.UserService;
import br.edu.utfpr.pb.projeto_ecommerce.server.shared.GenericResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // fala para o Spring que essa classe vai receber e tratar requisições http
// a requisição vem de fora pra dentro e nao da classe pra fora
@RequestMapping("users") // siginifica que todos os metodos teram de base o /users
public class UserController {
    private final UserService userService; // ela vai usar como atributo um elemento service
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper){ // declara no construtor porque ele vai usar
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @PostMapping// informa que esse metodo tem que ser executado quando receber um post.
    // @ResponseStatus(HttpStatus.CREATED) // retorna a validacao do created que o usuario precisa
    // Vamores remover a notation do ResponseStatus porque dentro do objeto que retornamos ResponseEntity ja retorna o 201 created.
    public ResponseEntity<GenericResponse> createUser(@RequestBody @Valid UserDTO userDTO){
        // Aqui estamos informando que vamos retornar um GenericResponse um objeto de resposta.
        // @RequestBody -> Vamos receber uma requisição no corpo do JSON transformar em UserDTO
        // @Valid -> vai pegar o DTO que gerou e testar as validações delas, @NotBlank, @Size, etc.
        this.userService.save(userMapper.toEntity(userDTO));
        //Depois chamamos a função .save do Service que pega do atributo repository e passamos o userMapper
        //Para mapear para o UserDTO para model User e depois persistir esses dados no banco
        //
        return ResponseEntity // embalagem da resposta http
                .status(HttpStatus.CREATED) // dizendo qual Status HTTP da resposta
                .body(new GenericResponse("Usuário salvo com sucesso")); // conteudo da resposta
        // Retorne uma resposta HTTP com status 201 Created e, no corpo,
        // coloque uma mensagem dizendo Usuário salvo com sucesso.
    }
}
