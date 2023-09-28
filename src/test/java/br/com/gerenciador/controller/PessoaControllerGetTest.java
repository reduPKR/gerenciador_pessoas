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
import java.util.ArrayList;
import java.util.List;

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

    @Test
    void deve_retornar_lista_de_pessoas() {
        List<PessoaModel> pessoas = new ArrayList<>();
        pessoas.add(new PessoaModel(1L, "Rafael", LocalDate.of(1993, 1, 15)));
        pessoas.add(new PessoaModel(2L, "Eduardo", LocalDate.of(1994, 3, 20)));
        pessoas.add(new PessoaModel(3L, "Sara", LocalDate.of(1995, 5, 10)));
        pessoas.add(new PessoaModel(4L, "Juliane", LocalDate.of(1996, 7, 25)));
        pessoas.add(new PessoaModel(5L, "Enrique", LocalDate.of(1997, 9, 5)));
        pessoas.add(new PessoaModel(6L, "Nicolas", LocalDate.of(1998, 11, 30)));

        List<PessoaVisualizacaoResponse> visualizacaoResponses = new ArrayList<>();
        for (PessoaModel pessoa : pessoas) {
            visualizacaoResponses.add(new PessoaVisualizacaoResponse(pessoa.getId(), pessoa.getNome(), pessoa.getDataNascimento()));
        }

        when(pessoaService.listarPessoas()).thenReturn(pessoas);
        when(pessoaMapperVisualizacao.convertToResponse(any())).thenAnswer(invocation -> {
            PessoaModel pessoa = invocation.getArgument(0);
            for (PessoaVisualizacaoResponse response : visualizacaoResponses) {
                if (response.getId().equals(pessoa.getId())) {
                    return response;
                }
            }
            return null;
        });

        ResponseEntity<List<PessoaVisualizacaoResponse>> responseEntity = pessoaControllerGet.listarPessoas();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(visualizacaoResponses, responseEntity.getBody());

        verify(pessoaService, times(1)).listarPessoas();
        verify(pessoaMapperVisualizacao, times(pessoas.size())).convertToResponse(any());
    }
}