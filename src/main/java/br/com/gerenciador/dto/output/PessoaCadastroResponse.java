package br.com.gerenciador.dto.output;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class PessoaCadastroResponse {
    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private Set<EnderecoResponse> enderecos = new HashSet<>();

    public PessoaCadastroResponse() {
    }

    public PessoaCadastroResponse(Long id, String nome, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Set<EnderecoResponse> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(Set<EnderecoResponse> enderecos) {
        this.enderecos = enderecos;
    }

    public void addEndereco(EnderecoResponse endereco) {
        this.enderecos.add(endereco);
    }
}
