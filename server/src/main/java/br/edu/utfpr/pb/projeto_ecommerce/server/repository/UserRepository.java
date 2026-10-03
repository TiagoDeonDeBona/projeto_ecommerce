package br.edu.utfpr.pb.projeto_ecommerce.server.repository;

import br.edu.utfpr.pb.projeto_ecommerce.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    // Aqui é a forma que o UserRepository vai fazer a query no banco
    // find = Procure|User = Usuario|By = Pelo|Username = Nome
    // SELECT * FROM tb_user where nome = (oquevirnoparametro)
}
