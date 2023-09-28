package br.com.gerenciador.controller;

import br.com.gerenciador.dto.output.EnderecoResponse;
import br.com.gerenciador.mapper.PessoaMapperCadastro;
import br.com.gerenciador.models.EnderecoModel;
import br.com.gerenciador.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pessoas")
public class EnderecoControllerPut {
    private PessoaService service;
    private PessoaMapperCadastro mapper;

    @Autowired
    public EnderecoControllerPut(PessoaService service, PessoaMapperCadastro mapper) {
        this.service = service;
        this.mapper = mapper;
    }


    @PutMapping("/{pessoaId}/endereco/{enderecoId}/principal")
    public ResponseEntity<EnderecoResponse> setEnderecoPrincipal(
            @PathVariable Long pessoaId,
            @PathVariable Long enderecoId
    ) {
        Boolean result = service.cadastrarEnderecoPrincipal(pessoaId, enderecoId);

        if (result) {
            EnderecoModel enderecoModel = service.consultaEndereco(enderecoId);
            EnderecoResponse response = mapper.convertToResponse(enderecoModel);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
