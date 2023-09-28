package br.com.gerenciador.controller;

import br.com.gerenciador.dto.input.EnderecoCadastroRequest;
import br.com.gerenciador.dto.input.PessoaCadastroRequest;
import br.com.gerenciador.dto.output.EnderecoResponse;
import br.com.gerenciador.dto.output.PessoaCadastroResponse;
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
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaControllerPostTest {
    @InjectMocks
    private PessoaControllerPost pessoaControllerPost;

    @Mock
    private PessoaService pessoaService;

    @Mock
    private PessoaMapperCadastro pessoaMapperCadastro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deve_cadastrar_pessoa_sem_endereco(){
        LocalDate dataNascimento = LocalDate.of(1994, 2, 28);
        Set<EnderecoCadastroRequest> enderecos = new HashSet<>();

        PessoaCadastroRequest request = new PessoaCadastroRequest("Rafael", dataNascimento, enderecos);
        PessoaModel pessoaModel = new PessoaModel( "Rafael", dataNascimento);
        PessoaModel pessoaResponse = new PessoaModel(1L, "Rafael", dataNascimento);
        PessoaCadastroResponse pessoaCadastroResponse = new PessoaCadastroResponse(1L, "Rafael", dataNascimento);

        when(pessoaMapperCadastro.convertToModel(request)).thenReturn(pessoaModel);
        when(pessoaService.cadastrarPessoa(pessoaModel)).thenReturn(pessoaResponse);
        when(pessoaMapperCadastro.convertToResponse(pessoaResponse)).thenReturn(pessoaCadastroResponse);

        ResponseEntity<PessoaCadastroResponse> responseEntity = pessoaControllerPost.cadastrarPessoa(request);


        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        verify(pessoaMapperCadastro, times(1)).convertToModel(request);
        verify(pessoaService, times(1)).cadastrarPessoa(pessoaModel);
        verify(pessoaMapperCadastro, times(1)).convertToResponse(pessoaResponse);
    }

    @Test
    void deve_cadastrar_pessoa_com_um_endereco() {
        LocalDate dataNascimento = LocalDate.of(1994, 2, 28);

        EnderecoCadastroRequest endereco = new EnderecoCadastroRequest(
                TipoLogradouro.RUA, "12345-678", "123", "Presidente Prudente", true
        );

        Set<EnderecoCadastroRequest> enderecos = new HashSet<>();
        enderecos.add(endereco);

        PessoaCadastroRequest request = new PessoaCadastroRequest("Rafael", dataNascimento, enderecos);

        PessoaModel pessoaModel = new PessoaModel("Rafael", dataNascimento);
        pessoaModel.addEndereco(new EnderecoModel(TipoLogradouro.RUA, "12345-678", "123", "Presidente Prudente", true, pessoaModel));

        PessoaModel pessoaResponse = new PessoaModel(1L, "Rafael", dataNascimento);
        pessoaResponse.addEndereco(new EnderecoModel(1L, TipoLogradouro.RUA, "12345-678", "123", "Presidente Prudente", true, pessoaResponse));

        PessoaCadastroResponse pessoaCadastroResponse = new PessoaCadastroResponse(1L, "Rafael", dataNascimento);
        pessoaCadastroResponse.addEndereco(new EnderecoResponse(1L, TipoLogradouro.RUA, "12345-678", "123", "Presidente Prudente", true));

        when(pessoaMapperCadastro.convertToModel(request)).thenReturn(pessoaModel);
        when(pessoaService.cadastrarPessoa(pessoaModel)).thenReturn(pessoaResponse);
        when(pessoaMapperCadastro.convertToResponse(pessoaResponse)).thenReturn(pessoaCadastroResponse);

        ResponseEntity<PessoaCadastroResponse> responseEntity = pessoaControllerPost.cadastrarPessoa(request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        verify(pessoaMapperCadastro, times(1)).convertToModel(request);
        verify(pessoaService, times(1)).cadastrarPessoa(pessoaModel);
        verify(pessoaMapperCadastro, times(1)).convertToResponse(pessoaResponse);
    }

    @Test
    void deve_cadastrar_pessoa_com_dois_enderecos() {
        LocalDate dataNascimento = LocalDate.of(1994, 2, 28);

        // Criar dois endereços para o teste
        EnderecoCadastroRequest endereco1 = new EnderecoCadastroRequest(
                TipoLogradouro.RUA, "12345-678", "123", "Presidente Prudente", true
        );

        EnderecoCadastroRequest endereco2 = new EnderecoCadastroRequest(
                TipoLogradouro.AVENIDA, "54321-876", "456", "Maringá", false
        );

        Set<EnderecoCadastroRequest> enderecos = new HashSet<>();
        enderecos.add(endereco1);
        enderecos.add(endereco2);

        PessoaCadastroRequest request = new PessoaCadastroRequest("Rafael", dataNascimento, enderecos);

        PessoaModel pessoaModel = new PessoaModel("Rafael", dataNascimento);
        pessoaModel.addEndereco(new EnderecoModel(TipoLogradouro.RUA, "12345-678", "123", "Presidente Prudente", true, pessoaModel));
        pessoaModel.addEndereco(new EnderecoModel(TipoLogradouro.AVENIDA, "54321-876", "456", "Maringá", false, pessoaModel));

        PessoaModel pessoaResponse = new PessoaModel(1L, "Rafael", dataNascimento);
        pessoaResponse.addEndereco(new EnderecoModel(1L, TipoLogradouro.RUA, "12345-678", "123", "Presidente Prudente", true, pessoaResponse));
        pessoaResponse.addEndereco(new EnderecoModel(2L, TipoLogradouro.AVENIDA, "54321-876", "456", "Maringá", false, pessoaResponse));

        PessoaCadastroResponse pessoaCadastroResponse = new PessoaCadastroResponse(1L, "Rafael", dataNascimento);
        pessoaCadastroResponse.addEndereco(new EnderecoResponse(1L, TipoLogradouro.RUA, "12345-678", "123", "Presidente Prudente", true));
        pessoaCadastroResponse.addEndereco(new EnderecoResponse(2L, TipoLogradouro.AVENIDA, "54321-876", "456", "Maringá", false));

        when(pessoaMapperCadastro.convertToModel(request)).thenReturn(pessoaModel);
        when(pessoaService.cadastrarPessoa(pessoaModel)).thenReturn(pessoaResponse);
        when(pessoaMapperCadastro.convertToResponse(pessoaResponse)).thenReturn(pessoaCadastroResponse);

        ResponseEntity<PessoaCadastroResponse> responseEntity = pessoaControllerPost.cadastrarPessoa(request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        verify(pessoaMapperCadastro, times(1)).convertToModel(request);
        verify(pessoaService, times(1)).cadastrarPessoa(pessoaModel);
        verify(pessoaMapperCadastro, times(1)).convertToResponse(pessoaResponse);
    }

    @Test
    void deve_retornar_bad_request_no_cadastro() {
        LocalDate dataNascimento = LocalDate.of(1994, 2, 28);
        Set<EnderecoCadastroRequest> enderecos = new HashSet<>();

        PessoaCadastroRequest request = new PessoaCadastroRequest("Rafael", dataNascimento, enderecos);
        PessoaModel pessoaModel = new PessoaModel("Rafael", dataNascimento);

        when(pessoaMapperCadastro.convertToModel(request)).thenReturn(pessoaModel);
        when(pessoaService.cadastrarPessoa(pessoaModel)).thenReturn(null);

        ResponseEntity<PessoaCadastroResponse> responseEntity = pessoaControllerPost.cadastrarPessoa(request);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(pessoaMapperCadastro, times(1)).convertToModel(request);
        verify(pessoaService, times(1)).cadastrarPessoa(pessoaModel);
    }
}