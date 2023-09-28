package br.com.gerenciador.dto.output;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PessoaAtualizarResponse {
    private Long id;
    private String nome;
    private LocalDate dataNascimento;

    public PessoaAtualizarResponse() {
    }

    public PessoaAtualizarResponse(Long id, String nome, LocalDate dataNascimento) {
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
}
