package br.edu.utfpr.pb.projeto_ecommerce.server.service;

import br.edu.utfpr.pb.projeto_ecommerce.server.DTO.CalculoFreteDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class CalcularFreteService {
    private final RestClient restClient;
    private final String apiKey;

    public CalcularFreteService(@Value("${gecko.api.key}") String apiKey) {
        this.restClient = RestClient.create("https://api.geckoapi.com.br");
        this.apiKey = apiKey;
    }

    public BigDecimal consultarFrete(String cepDestino){
        Map<String, Object> dados = Map.of( // mapeia os dados que vamos enviar
                "target", "correios.com.br", //
                "type", "quote",
                "originZipCode", "01001000", // coloquei origem longe porque os cep destino é tudo perto
                "destinationZipCode", cepDestino,
                "packageHeightCm", 10,
                "packageWidthCm", 15,
                "packageLengthCm", 20
        );

        CalculoFreteDTO resposta = restClient.post()
                .uri("/v1/extract")
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dados)
                .retrieve()
                .body(CalculoFreteDTO.class);

        if (resposta == null ||
            resposta.getData() == null ||
            resposta.getData().getOptions() == null ||
            resposta.getData().getOptions().isEmpty()) {
            throw  new IllegalStateException("Nao foi possivel calcular o frete.");
        }

        // retorna o valor que da dnetro do objeto data que ta dentro do objeto options no primeiro objeto pega o preco
        BigDecimal valorFrete = resposta.getData().getOptions().get(0).getPriceAppOrSite();

        if(valorFrete == null) {
            throw  new IllegalStateException("Valor do frete indisponível.");
        }

        return valorFrete;
    }
}
