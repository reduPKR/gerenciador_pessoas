package br.com.gerenciador.dto.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PessoaAtualizarRequest {
    @NotEmpty
    @Size(max = 100)
    private String nome;
    @NotNull
    private LocalDate dataNascimento;

    public PessoaAtualizarRequest() {
    }

    public PessoaAtualizarRequest(String nome, @NotNull LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }


    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

}
