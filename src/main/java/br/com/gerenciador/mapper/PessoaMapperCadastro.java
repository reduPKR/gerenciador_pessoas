package br.com.gerenciador.mapper;

import br.com.gerenciador.dto.input.EnderecoCadastroRequest;
import br.com.gerenciador.dto.input.PessoaCadastroRequest;
import br.com.gerenciador.dto.output.EnderecoCadastroResponse;
import br.com.gerenciador.dto.output.PessoaCadastroResponse;
import br.com.gerenciador.models.EnderecoModel;
import br.com.gerenciador.models.PessoaModel;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PessoaMapperCadastro {
    public PessoaModel convertToModel(PessoaCadastroRequest request) {
        PessoaModel pessoaModel = new PessoaModel();
        pessoaModel.setNome(request.getNome());
        pessoaModel.setDataNascimento(request.getDataNascimento());

        Set<EnderecoModel> enderecoModels = request.getEnderecos()
                .stream()
                .map(enderecoRequest -> convertToModel(enderecoRequest, pessoaModel))
                .collect(Collectors.toSet());

        pessoaModel.setEnderecos(enderecoModels);

        return pessoaModel;
    }

    public EnderecoModel convertToModel(EnderecoCadastroRequest request, PessoaModel pessoa) {
        EnderecoModel enderecoModel = new EnderecoModel();
        enderecoModel.setLogradouro(request.getLogradouro());
        enderecoModel.setCep(request.getCep());
        enderecoModel.setNumero(request.getNumero());
        enderecoModel.setCidade(request.getCidade());
        enderecoModel.setEnderecoPrincipal(request.isEnderecoPrincipal());
        enderecoModel.setPessoa(pessoa);

        return enderecoModel;
    }

    public PessoaCadastroResponse convertToResponse(PessoaModel pessoaModel) {
        PessoaCadastroResponse pessoaResponse = new PessoaCadastroResponse();
        pessoaResponse.setId(pessoaModel.getId());
        pessoaResponse.setNome(pessoaModel.getNome());
        pessoaResponse.setDataNascimento(pessoaModel.getDataNascimento());

        Set<EnderecoCadastroResponse> enderecoResponses = pessoaModel.getEnderecos()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toSet());

        pessoaResponse.setEnderecos(enderecoResponses);

        return pessoaResponse;
    }

    public EnderecoCadastroResponse convertToResponse(EnderecoModel enderecoModel) {
        EnderecoCadastroResponse enderecoResponse = new EnderecoCadastroResponse();
        enderecoResponse.setId(enderecoModel.getId());
        enderecoResponse.setLogradouro(enderecoModel.getLogradouro());
        enderecoResponse.setCep(enderecoModel.getCep());
        enderecoResponse.setNumero(enderecoModel.getNumero());
        enderecoResponse.setCidade(enderecoModel.getCidade());
        enderecoResponse.setEnderecoPrincipal(enderecoModel.isEnderecoPrincipal());

        return enderecoResponse;
    }
}
