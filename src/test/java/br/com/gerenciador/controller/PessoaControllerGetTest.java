package br.com.gerenciador.controller;

import br.com.gerenciador.dto.output.PessoaVisualizacaoResponse;
import br.com.gerenciador.mapper.PessoaMapperVisualizacao;
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
class PessoaControllerGetTest {
    @InjectMocks
    private PessoaControllerGet pessoaControllerGet;

    @Mock
    private PessoaService pessoaService;

    @Mock
    private PessoaMapperVisualizacao pessoaMapperVisualizacao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deve_retornar_pessoa_por_id_quando_existir() {
        Long id = 1L;
        PessoaModel pessoaModel = new PessoaModel(1L, "Rafael", LocalDate.of(1994, 2, 28));
        PessoaVisualizacaoResponse pessoaVisualizacaoResponse = new PessoaVisualizacaoResponse(1L, "Rafael", LocalDate.of(1994, 2, 28));

        when(pessoaService.consultarPessoa(id)).thenReturn(pessoaModel);
        when(pessoaMapperVisualizacao.convertToResponse(pessoaModel)).thenReturn(pessoaVisualizacaoResponse);

        ResponseEntity<PessoaVisualizacaoResponse> responseEntity = pessoaControllerGet.getPessoaById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(pessoaVisualizacaoResponse, responseEntity.getBody());

        verify(pessoaService, times(1)).consultarPessoa(id);
        verify(pessoaMapperVisualizacao, times(1)).convertToResponse(pessoaModel);
    }

    @Test
    void deve_retornar_not_found_quando_pessoa_nao_existir() {
        Long id = 1L;

        when(pessoaService.consultarPessoa(id)).thenReturn(null);

        ResponseEntity<PessoaVisualizacaoResponse> responseEntity = pessoaControllerGet.getPessoaById(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        verify(pessoaService, times(1)).consultarPessoa(id);
        verify(pessoaMapperVisualizacao, never()).convertToResponse(any());
    }
}