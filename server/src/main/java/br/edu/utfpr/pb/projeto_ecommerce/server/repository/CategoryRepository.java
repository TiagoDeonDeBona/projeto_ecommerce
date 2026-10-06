package br.edu.utfpr.pb.projeto_ecommerce.server.repository;

import br.edu.utfpr.pb.projeto_ecommerce.server.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
