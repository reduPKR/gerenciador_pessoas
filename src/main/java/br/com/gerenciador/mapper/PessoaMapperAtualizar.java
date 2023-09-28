package br.com.gerenciador.mapper;

import br.com.gerenciador.dto.input.PessoaAtualizarRequest;
import br.com.gerenciador.dto.output.PessoaAtualizarResponse;
import br.com.gerenciador.models.PessoaModel;
import org.springframework.stereotype.Component;

@Component
public class PessoaMapperAtualizar {
    public PessoaModel convertToModel(PessoaAtualizarRequest request) {
        PessoaModel pessoaModel = new PessoaModel();
        pessoaModel.setNome(request.getNome());
        pessoaModel.setDataNascimento(request.getDataNascimento());

        return pessoaModel;
    }

    public PessoaAtualizarResponse convertToResponse(PessoaModel pessoaModel) {
        PessoaAtualizarResponse pessoaResponse = new PessoaAtualizarResponse();
        pessoaResponse.setId(pessoaModel.getId());
        pessoaResponse.setNome(pessoaModel.getNome());
        pessoaResponse.setDataNascimento(pessoaModel.getDataNascimento());

        return pessoaResponse;
    }
}
