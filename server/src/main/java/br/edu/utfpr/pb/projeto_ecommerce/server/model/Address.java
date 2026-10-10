package br.edu.utfpr.pb.projeto_ecommerce.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "tb_address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 8, max = 8)
    @Column(length = 8, nullable = false)
    @Pattern(regexp = "^\\d+$") // aceitar so numeros
    private String cep;

    @NotBlank
    @Size(min = 2, max = 70)
    @Column(length = 70, nullable = false)
    private String rua;

    @NotBlank
    @Size(min = 1, max = 5)
    @Column(length = 70, nullable = false)
    private String numero;

    @NotBlank
    @Size(min = 1, max = 70)
    @Column(length = 70)
    private String complemento;

    @NotBlank
    @Size(min = 4, max = 25)
    @Column(length = 25, nullable = false)
    private String estado;

    @NotBlank
    @Size(min = 3, max = 40)
    @Column(length = 40, nullable = false)
    private String cidade;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column(length = 20, nullable = false)
    private String regiao;

    @NotBlank
    @Size(min = 2, max = 2)
    @Column(length = 2, nullable = false)
    private String uf;

    @NotBlank
    @Size(min = 2, max = 3)
    @Column(length = 3)
    private String ddd;

    @NotBlank
    @Size(max = 70)
    @Column(length = 70)
    private String nome_recebimento;

    @NotBlank
    @Size(max = 20)
    @Column(length = 20)
    private String telefone_contato;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
}
