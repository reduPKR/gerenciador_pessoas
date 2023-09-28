package br.com.gerenciador.controller;

import br.com.gerenciador.dto.input.PessoaAtualizarRequest;
import br.com.gerenciador.dto.output.PessoaAtualizarResponse;
import br.com.gerenciador.mapper.PessoaMapperAtualizar;
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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaControllerPutTest {
    @InjectMocks
    private PessoaControllerPut pessoaControllerPut;

    @Mock
    private PessoaService pessoaService;

    @Mock
    private PessoaMapperAtualizar pessoaMapperAtualizar;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deve_atualizar_pessoa() {
        Long id = 1L;
        PessoaAtualizarRequest request = new PessoaAtualizarRequest("Rafael", LocalDate.of(1994, 2, 28));
        PessoaAtualizarResponse response = new PessoaAtualizarResponse(id, "Rafael", LocalDate.of(1994, 2, 28));

        PessoaModel pessoaModel = new PessoaModel();
        pessoaModel.setId(id);

        when(pessoaMapperAtualizar.convertToModel(request)).thenReturn(pessoaModel);
        when(pessoaService.editarPessoa(pessoaModel)).thenReturn(true);
        when(pessoaMapperAtualizar.convertToResponse(pessoaModel)).thenReturn(response);

        ResponseEntity<PessoaAtualizarResponse> responseEntity = pessoaControllerPut.atualizarPessoa(id, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(id, Objects.requireNonNull(responseEntity.getBody()).getId());
        assertEquals(request.getNome(), responseEntity.getBody().getNome());
        assertEquals(request.getDataNascimento(), responseEntity.getBody().getDataNascimento());

        verify(pessoaMapperAtualizar, times(1)).convertToModel(request);
        verify(pessoaService, times(1)).editarPessoa(pessoaModel);
        verify(pessoaMapperAtualizar, times(1)).convertToResponse(pessoaModel);
    }

    @Test
    void testAtualizarPessoa_Failure() {
        Long id = 1L;
        PessoaAtualizarRequest request = new PessoaAtualizarRequest("Rafael", LocalDate.of(1994, 2, 28));
        PessoaAtualizarResponse response = new PessoaAtualizarResponse(id, "Rafael", LocalDate.of(1994, 2, 28));

        PessoaModel pessoaModel = new PessoaModel();
        pessoaModel.setId(id);

        when(pessoaMapperAtualizar.convertToModel(request)).thenReturn(pessoaModel);
        when(pessoaService.editarPessoa(pessoaModel)).thenReturn(false);

        ResponseEntity<PessoaAtualizarResponse> responseEntity = pessoaControllerPut.atualizarPessoa(id, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        verify(pessoaMapperAtualizar, times(1)).convertToModel(request);
        verify(pessoaService, times(1)).editarPessoa(pessoaModel);
    }
}