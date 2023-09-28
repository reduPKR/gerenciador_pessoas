package br.com.gerenciador.dto.output;

import br.com.gerenciador.enums.TipoLogradouro;
import org.springframework.stereotype.Component;

@Component
public class EnderecoResponse {
    private Long id;
    private TipoLogradouro logradouro;
    private String cep;
    private String numero;
    private String cidade;
    private boolean enderecoPrincipal;

    public EnderecoResponse() {
    }

    public EnderecoResponse(Long id, TipoLogradouro logradouro, String cep, String numero, String cidade, boolean enderecoPrincipal) {
        this.id = id;
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.enderecoPrincipal = enderecoPrincipal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
