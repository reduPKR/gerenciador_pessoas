package br.com.gerenciador.dto.input;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class PessoaCadastroRequest {
    @NotEmpty
    @Size(max = 100)
    private String nome;
    @NotEmpty
    private LocalDate dataNascimento;

    private Set<EnderecoCadastroRequest> enderecos = new HashSet<>();

    public PessoaCadastroRequest() {
    }

    public PessoaCadastroRequest(String nome, LocalDate dataNascimento, Set<EnderecoCadastroRequest> enderecos) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.enderecos = enderecos;
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

    public Set<EnderecoCadastroRequest> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(Set<EnderecoCadastroRequest> enderecos) {
        this.enderecos = enderecos;
    }
}
