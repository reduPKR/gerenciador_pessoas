package br.com.gerenciador.controller;

import br.com.gerenciador.dto.output.EnderecoCadastroResponse;
import br.com.gerenciador.mapper.PessoaMapperCadastro;
import br.com.gerenciador.models.EnderecoModel;
import br.com.gerenciador.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pessoas")
public class EnderecoControllerGet {
    private PessoaService pessoaService;
    private PessoaMapperCadastro mapper;

    @Autowired
    public EnderecoControllerGet(PessoaService pessoaService, PessoaMapperCadastro mapper) {
        this.pessoaService = pessoaService;
        this.mapper = mapper;
    }

    @GetMapping("/{pessoaId}/endereco")
    public ResponseEntity<Set<EnderecoCadastroResponse>> getEnderecosByPessoaId(@PathVariable Long pessoaId) {
        Set<EnderecoModel> enderecos = pessoaService.listarEnderecos(pessoaId);

        if (!enderecos.isEmpty()) {
            Set<EnderecoCadastroResponse> enderecoResponses = enderecos.stream()
                    .map(mapper::convertToResponse)
                    .collect(Collectors.toSet());

            return new ResponseEntity<>(enderecoResponses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
