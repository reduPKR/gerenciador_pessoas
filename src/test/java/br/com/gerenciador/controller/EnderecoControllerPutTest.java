package br.com.gerenciador.controller;

import br.com.gerenciador.dto.output.EnderecoResponse;
import br.com.gerenciador.enums.TipoLogradouro;
import br.com.gerenciador.mapper.PessoaMapperCadastro;
import br.com.gerenciador.models.EnderecoModel;
import br.com.gerenciador.services.PessoaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnderecoControllerPutTest {
    @InjectMocks
    private EnderecoControllerPut enderecoControllerPut;

    @Mock
    private PessoaService pessoaService;

    @Mock
    private PessoaMapperCadastro pessoaMapperCadastro;

    @Test
    public void deve_alterar_endereco_principal() {
        Long pessoaId = 1L;
        Long enderecoId = 2L;

        EnderecoModel enderecoModel =
                new EnderecoModel(enderecoId, TipoLogradouro.RUA, "12345-678", "123", "Maringá", true, null);
        EnderecoResponse enderecoResponse =
                new EnderecoResponse(enderecoId, TipoLogradouro.RUA, "12345-678", "123", "Maringá", true);

        when(pessoaService.cadastrarEnderecoPrincipal(pessoaId, enderecoId)).thenReturn(true);
        when(pessoaService.consultaEndereco(enderecoId)).thenReturn(enderecoModel);
        when(pessoaMapperCadastro.convertToResponse(enderecoModel)).thenReturn(enderecoResponse);

        ResponseEntity<EnderecoResponse> responseEntity = enderecoControllerPut.setEnderecoPrincipal(pessoaId, enderecoId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        EnderecoResponse response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(enderecoId, response.getId());
        assertEquals(TipoLogradouro.RUA, response.getLogradouro());
        assertEquals("12345-678", response.getCep());
        assertEquals("123", response.getNumero());
        assertEquals("Maringá", response.getCidade());
        assertTrue(response.isEnderecoPrincipal());
    }

    @Test
    public void deve_retornar_notfound() {
        Long pessoaId = 1L;
        Long enderecoId = 2L;

        when(pessoaService.cadastrarEnderecoPrincipal(pessoaId, enderecoId)).thenReturn(false);

        ResponseEntity<EnderecoResponse> responseEntity = enderecoControllerPut.setEnderecoPrincipal(pessoaId, enderecoId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}