package br.edu.utfpr.pb.projeto_ecommerce.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "tb_category")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(length = 100, nullable = false, unique = true)
    private String name;

    // por mais que nao tenha nenhuma interface herdando diretamente a classe
    // todas classes herdam a classe Object e a classe object tem o metodo equals
    // estamos trocando a predefiniçãoa dessa função para reconhecer 2 categorias iguais
    // somente se tiverem o mesmo ID
    // as 3 primeiras linhas continua iguais
    @Override
    public boolean equals(Object o) {
        if(this == o) return true; // verifica se o objeto que eu passei é igual a ele mesmo
        if(o == null || getClass() != o.getClass()) return false; // verifica se ele nao é nulo e nao é de outra classe
        Category category = (Category) o; // defino que é meu objeto do parametro é uma categoria
        return Objects.equals(id, category.id); // ai faço as validacoes dos ID
    }

    // mesma coisa com o hashCode vai ser calculado a partir do id
    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
