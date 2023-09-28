package br.com.gerenciador.dto.input;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class PessoaCadastroRequest {
    @NotEmpty
    @Size(max = 100)
    private String nome;
    @NotNull
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


    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public Set<EnderecoCadastroRequest> getEnderecos() {
        return enderecos;
    }

}
