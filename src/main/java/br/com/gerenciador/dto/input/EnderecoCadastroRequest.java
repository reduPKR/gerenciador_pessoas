package br.com.gerenciador.dto.input;

import br.com.gerenciador.enums.TipoLogradouro;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class EnderecoCadastroRequest {
    @NotNull
    private TipoLogradouro logradouro;
    @NotEmpty
    private String cep;
    @NotEmpty
    private String numero;
    @NotEmpty
    private String cidade;
    @NotNull
    private boolean enderecoPrincipal;

    public EnderecoCadastroRequest() {
    }

    public EnderecoCadastroRequest(TipoLogradouro logradouro, String cep, String numero, String cidade, boolean enderecoPrincipal) {
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.enderecoPrincipal = enderecoPrincipal;
    }

    public TipoLogradouro getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(TipoLogradouro logradouro) {
        this.logradouro = logradouro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public boolean isEnderecoPrincipal() {
        return enderecoPrincipal;
    }

    public void setEnderecoPrincipal(boolean enderecoPrincipal) {
        this.enderecoPrincipal = enderecoPrincipal;
    }
}
