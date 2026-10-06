package br.edu.utfpr.pb.projeto_ecommerce.server.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CategoryDTO {
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

}
