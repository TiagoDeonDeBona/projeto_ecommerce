package br.edu.utfpr.pb.projeto_ecommerce.server.service;

import br.edu.utfpr.pb.projeto_ecommerce.server.DTO.ViaCepDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ViaCepService {
    private final RestClient restClient; // metodo do spring conseguir fazer requisicoes para ouutro sistema

    public ViaCepService(){ // nao passa parametro no construtor porque ele nao usa nenhuma dependencia e sim cria sua propia dependencia
        this.restClient = RestClient.create("https://viacep.com.br");
        // estamos dizendo que quando eu usar esse malandro vai ser para conversar com o sistema da viacep
    }

    public ViaCepDTO consultarCep(String cep){ // recebe um cep e retorna um objeto DTO
        if (cep == null || !cep.matches("\\d{8}")){ // aqui o (//d) siginifica digito numero e o {8} é a quantidade de vezes
            throw new IllegalArgumentException("O CEP deve conter exatamente 8 numeros.");
        }

        ViaCepDTO endereco = restClient.get()// prepara a requisição HTTP GET
                .uri("/ws/{cep}/json/", cep) // monta o caminho da consulta
                //ws/: parte fixa da rota definida pelo ViaCEP para consultas.
                //{cep}: uma variável que será substituída pelo CEP informado.
                //json/: indica que queremos receber a resposta no formato JSON.
                .retrieve() // executa a requisicao e obtem a resposta
                .body(ViaCepDTO.class); // converte o JSON recebito em um DTO

        if (endereco == null || Boolean.TRUE.equals(endereco.getErro())){
            return null;
        }

        return endereco;
    }


}
