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

    public String getCep() {
        return cep;
    }

    public String getNumero() {
        return numero;
    }

    public String getCidade() {
        return cidade;
    }

    public boolean isEnderecoPrincipal() {
        return enderecoPrincipal;
    }

}
