package br.com.gerenciador.models;

import br.com.gerenciador.enums.TipoLogradouro;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "endereco")
public class EnderecoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoLogradouro logradouro;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private String cidade;

    @Column(name = "endereco_principal", nullable = false)
    private boolean enderecoPrincipal;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private PessoaModel pessoa;

    public EnderecoModel() {
    }

    public EnderecoModel(TipoLogradouro logradouro, String cep, String numero, String cidade, boolean enderecoPrincipal, PessoaModel pessoa) {
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.enderecoPrincipal = enderecoPrincipal;
        this.pessoa = pessoa;
    }
}
