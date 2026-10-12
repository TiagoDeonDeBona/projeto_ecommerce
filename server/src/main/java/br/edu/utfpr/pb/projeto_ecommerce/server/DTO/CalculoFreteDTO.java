package br.edu.utfpr.pb.projeto_ecommerce.server.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

// Aqui basicamente vai fazer oque
// a api do Gecko retorna um objeto data : {...}
// dentro desse data tem um outro objeto options data : {..., options : {...}}
// ai dentro do options vao ter os options[0] , options[1] por default vamos pegar o 0
// ai dentro das opcoes tem o priceAppOrSite que é o valor do frete

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CalculoFreteDTO {
    private Dados data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Dados {
        List<Opcao> options;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Opcao {
        private BigDecimal priceAppOrSite;
    }

}
