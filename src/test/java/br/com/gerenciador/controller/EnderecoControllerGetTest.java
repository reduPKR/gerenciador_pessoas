package br.com.gerenciador.controller;

import br.com.gerenciador.dto.output.EnderecoResponse;
import br.com.gerenciador.enums.TipoLogradouro;
import br.com.gerenciador.mapper.PessoaMapperCadastro;
import br.com.gerenciador.models.EnderecoModel;
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

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnderecoControllerGetTest {
    @InjectMocks
    private EnderecoControllerGet enderecoControllerGet;

    @Mock
    private PessoaService pessoaService;

    @Mock
    private PessoaMapperCadastro pessoaMapperCadastro;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deve_retornar_enderecos_da_pessoa() {
        Long pessoaId = 1L;
        Set<EnderecoModel> enderecoModels = new HashSet<>();
        enderecoModels.add(new EnderecoModel(1L, TipoLogradouro.RUA, "12345-678", "123", "Maringa", true, null));
        enderecoModels.add(new EnderecoModel(2L, TipoLogradouro.AVENIDA, "54321-876", "456", "Curitiba", false, null));

        when(pessoaService.listarEnderecos(pessoaId)).thenReturn(enderecoModels);

        Set<EnderecoResponse> enderecoResponses = enderecoModels.stream()
                .map(pessoaMapperCadastro::convertToResponse)
                .collect(Collectors.toSet());

        ResponseEntity<Set<EnderecoResponse>> responseEntity = enderecoControllerGet.getEnderecosByPessoaId(pessoaId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(enderecoResponses, responseEntity.getBody());

        assertEquals(enderecoResponses.size(), Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    public void shouldReturnNotFoundWhenNoAddressesExist() {
        Long pessoaId = 2L;

        when(pessoaService.listarEnderecos(pessoaId)).thenReturn(Collections.emptySet());

        ResponseEntity<Set<EnderecoResponse>> responseEntity = enderecoControllerGet.getEnderecosByPessoaId(pessoaId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}