package br.edu.utfpr.pb.projeto_ecommerce.server.service;

import br.edu.utfpr.pb.projeto_ecommerce.server.model.User;
import br.edu.utfpr.pb.projeto_ecommerce.server.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // classe de serviço gerenciada pelo spring
// implements está dizendo que meu AuthService vai cumprir o contrato que o Spring Security exige de
// uma classe responsavel por encontrar usuarios
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // procura no banco o usuario que recebeu o nome na requisição http
        User user = userRepository.findUserByUsername(username);

        // caso nao achar lança a exceção de usuario nao encontrado
        if (user == null){
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }
        // caso achar retornamos o user porque nossa classe user implementa o UserDetails
        // ele vai retornar o objeto que tem as credencias que preciso para autenticar
        return user;
    }
}
