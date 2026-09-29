package br.edu.utfpr.pb.projeto_ecommerce.server.repository;

import br.edu.utfpr.pb.projeto_ecommerce.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Ela nao vai ter nenhuma declaração porque o Spring Data JPA ja faz isso automaticamente
    // É colocado como interface para a responsabilidade de implantação desses metodos ficar com o SPRING e nao
    // com quem fazer a classe
}
