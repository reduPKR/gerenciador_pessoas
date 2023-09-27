package br.com.gerenciador.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pessoa")
public class PessoaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 100)
    private String nome;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EnderecoModel> enderecos = new HashSet<>();

    public PessoaModel() {
    }

    public PessoaModel(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public PessoaModel(Long id, String nome, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }


    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }


    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public Set<EnderecoModel> getEnderecos() {
        return enderecos;
    }

    public void addEndereco(EnderecoModel endereco) {
        this.enderecos.add(endereco);
    }

}
