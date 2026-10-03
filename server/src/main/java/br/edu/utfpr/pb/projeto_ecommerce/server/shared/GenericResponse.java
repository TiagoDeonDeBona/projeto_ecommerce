package br.edu.utfpr.pb.projeto_ecommerce.server.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericResponse {
    private String message;
}
