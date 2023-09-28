package br.com.gerenciador.mapper;

import br.com.gerenciador.dto.output.PessoaVisualizacaoResponse;
import br.com.gerenciador.models.PessoaModel;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PessoaMapperVisualizacaoTest {

    @Test
    public void deve_converter_model_para_response() {
        PessoaModel pessoaModel = mock(PessoaModel.class);

        when(pessoaModel.getId()).thenReturn(1L);
        when(pessoaModel.getNome()).thenReturn("Rafael");
        when(pessoaModel.getDataNascimento()).thenReturn(LocalDate.of(1994, 2, 28));

        PessoaMapperVisualizacao mapper = new PessoaMapperVisualizacao();

        PessoaVisualizacaoResponse response = mapper.convertToResponse(pessoaModel);

        assertEquals(pessoaModel.getId(), response.getId());
        assertEquals(pessoaModel.getNome(), response.getNome());
        assertEquals(pessoaModel.getDataNascimento(), response.getDataNascimento());
    }
}