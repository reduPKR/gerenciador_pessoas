package br.com.gerenciador.mapper;

import br.com.gerenciador.dto.input.PessoaCadastroRequest;
import br.com.gerenciador.dto.output.EnderecoCadastroResponse;
import br.com.gerenciador.dto.output.PessoaCadastroResponse;
import br.com.gerenciador.enums.TipoLogradouro;
import br.com.gerenciador.models.EnderecoModel;
import br.com.gerenciador.models.PessoaModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PessoaMapperTest {
    private final PessoaMapper mapper = new PessoaMapper();

    @Test
    void deve_converter_PessoaCadastroRequest_Para_PessoaModel() {
        PessoaCadastroRequest request =
                new PessoaCadastroRequest("Rafael", LocalDate.of(1994, 2, 28), new HashSet<>());

        PessoaModel pessoaModel = mapper.convertToModel(request);

        assertEquals(request.getNome(), pessoaModel.getNome());
        assertEquals(request.getDataNascimento(), pessoaModel.getDataNascimento());
        assertEquals(0, pessoaModel.getEnderecos().size());
    }

    @Test
    void deve_Converter_PessoaModel_Para_PessoaCadastroResponse() {
        PessoaModel pessoaModel = new PessoaModel(1L, "Rafael", LocalDate.of(1994, 2, 28));
        pessoaModel.addEndereco(
                new EnderecoModel(1L, TipoLogradouro.RUA, "12345-678", "123", "Presidente Prudente", true, pessoaModel)
        );

        PessoaCadastroResponse pessoaResponse = mapper.convertToResponse(pessoaModel);

        assertEquals(pessoaModel.getId(), pessoaResponse.getId());
        assertEquals(pessoaModel.getNome(), pessoaResponse.getNome());
        assertEquals(pessoaModel.getDataNascimento(), pessoaResponse.getDataNascimento());
        assertEquals(1, pessoaResponse.getEnderecos().size());

        EnderecoCadastroResponse enderecoResponse = pessoaResponse.getEnderecos().iterator().next();
        EnderecoModel enderecoModel = pessoaModel.getEnderecos().iterator().next();

        assertEquals(enderecoModel.getId(), enderecoResponse.getId());
        assertEquals(enderecoModel.getLogradouro(), enderecoResponse.getLogradouro());
        assertEquals(enderecoModel.getCep(), enderecoResponse.getCep());
        assertEquals(enderecoModel.getNumero(), enderecoResponse.getNumero());
        assertEquals(enderecoModel.getCidade(), enderecoResponse.getCidade());
        assertEquals(enderecoModel.isEnderecoPrincipal(), enderecoResponse.isEnderecoPrincipal());
    }
}