package br.edu.utfpr.pb.projeto_ecommerce.server.service;

import br.edu.utfpr.pb.projeto_ecommerce.server.model.User;
import br.edu.utfpr.pb.projeto_ecommerce.server.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService { // nao extends UserRepository porque ele nao herda um repository
                          // e sim usa um repository tem diferença
    private final UserRepository userRepository;
    // final garante que esse repository so vai ser usado para o user e pra mais ninguem

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    } // aqui basicamente para eu criar um serviço eu vou ter que criar um repository

    // vai receber um user e ter que retornar um user
    public User save(User user) {
        return this.userRepository.save(user); // aqui é so para deixar implicito porém podia ser
        //return userRepository.save(user);
    }
}
