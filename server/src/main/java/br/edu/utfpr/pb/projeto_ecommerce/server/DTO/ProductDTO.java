package br.edu.utfpr.pb.projeto_ecommerce.server.DTO;

import br.edu.utfpr.pb.projeto_ecommerce.server.model.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(min = 2, max = 255)
    private String description;

    @NotBlank
    private BigDecimal price;

    private CategoryDTO category;
}
