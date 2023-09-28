package br.com.gerenciador.mapper;

import br.com.gerenciador.dto.output.PessoaVisualizacaoResponse;
import br.com.gerenciador.models.PessoaModel;
import org.springframework.stereotype.Component;

@Component
public class PessoaMapperVisualizacao {
    public PessoaVisualizacaoResponse convertToResponse(PessoaModel pessoaModel) {
        PessoaVisualizacaoResponse pessoaResponse = new PessoaVisualizacaoResponse();
        pessoaResponse.setId(pessoaModel.getId());
        pessoaResponse.setNome(pessoaModel.getNome());
        pessoaResponse.setDataNascimento(pessoaModel.getDataNascimento());

        return pessoaResponse;
    }
}
