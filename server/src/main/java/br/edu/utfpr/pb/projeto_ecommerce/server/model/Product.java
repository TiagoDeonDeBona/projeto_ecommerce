package br.edu.utfpr.pb.projeto_ecommerce.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "tb_product")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(length = 100, nullable = false)
    private String name;

    @NotBlank
    @Size(min = 2, max = 255)
    @Column(length = 100)
    private String description;

    @NotBlank
    private BigDecimal price;

    @ManyToOne
    // o name = "category_id" vai ser o nome da coluna estrangeira na tabela de product
    // e o referencedColmnName id é a coluna da model user que vai se relacionar
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
