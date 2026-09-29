package br.edu.utfpr.pb.projeto_ecommerce.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity // a anotação mais importante aqui determina que essa classe
// nao vai ser uma simples classe e sim pode ser persistida no banco
@Table(name = "tb_user") //na tabela tb_user
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String displayName;
    private String password;
}
