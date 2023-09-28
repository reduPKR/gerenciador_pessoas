package br.com.gerenciador.controller;

import br.com.gerenciador.dto.input.EnderecoCadastroRequest;
import br.com.gerenciador.dto.output.EnderecoResponse;
import br.com.gerenciador.enums.TipoLogradouro;
import br.com.gerenciador.mapper.PessoaMapperCadastro;
import br.com.gerenciador.models.EnderecoModel;
import br.com.gerenciador.models.PessoaModel;
import br.com.gerenciador.services.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnderecoControllerPostTest {
    @InjectMocks
    private EnderecoControllerPost enderecoControllerPost;

    @Mock
    private PessoaService pessoaService;

    @Mock
    private PessoaMapperCadastro pessoaMapperCadastro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deve_cadastrar_endereco_com_sucesso() {
        Long pessoaId = 1L;
        PessoaModel pessoaModel = new PessoaModel("Rafael", LocalDate.of(1994, 2, 28));

        EnderecoCadastroRequest request = new EnderecoCadastroRequest(
                TipoLogradouro.RUA, "12345-678", "123", "São Paulo", true
        );

        EnderecoModel enderecoModel = new EnderecoModel(
                TipoLogradouro.RUA, "12345-678", "123", "São Paulo", true, null
        );

        when(pessoaMapperCadastro.convertToModel(request, null)).thenReturn(enderecoModel);


        enderecoModel.setPessoa(pessoaModel);
        EnderecoModel enderecoResponse = new EnderecoModel(
                1L, TipoLogradouro.RUA, "12345-678", "123", "São Paulo", true, pessoaModel
        );
        when(pessoaService.criarEndereco(pessoaId, enderecoModel)).thenReturn(enderecoResponse);

        EnderecoResponse enderecoCadastroResponse = new EnderecoResponse(
                1L, TipoLogradouro.RUA, "12345-678", "123", "São Paulo", true
        );
        when(pessoaMapperCadastro.convertToResponse(enderecoResponse)).thenReturn(enderecoCadastroResponse);

        ResponseEntity<EnderecoResponse> responseEntity = enderecoControllerPost.cadastrarEndereco(pessoaId, request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        EnderecoResponse responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(1L, responseBody.getId().longValue());
        assertEquals(TipoLogradouro.RUA, responseBody.getLogradouro());
        assertEquals("12345-678", responseBody.getCep());
        assertEquals("123", responseBody.getNumero());
        assertEquals("São Paulo", responseBody.getCidade());
        assertTrue(responseBody.isEnderecoPrincipal());
    }

    @Test
    void deve_retornar_bad_request_quando_cadastro_falha() {
        Long pessoaId = 1L;
        PessoaModel pessoaModel = new PessoaModel("Rafael", LocalDate.of(1994, 2, 28));

        EnderecoCadastroRequest request = new EnderecoCadastroRequest(
                TipoLogradouro.RUA, "12345-678", "123", "São Paulo", true
        );

        EnderecoModel enderecoModel = new EnderecoModel(
                TipoLogradouro.RUA, "12345-678", "123", "São Paulo", true, null
        );
        when(pessoaMapperCadastro.convertToModel(request, null)).thenReturn(enderecoModel);

        when(pessoaService.criarEndereco(pessoaId, enderecoModel)).thenReturn(null);

        ResponseEntity<EnderecoResponse> responseEntity = enderecoControllerPost.cadastrarEndereco(pessoaId, request);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}