package br.com.gerenciador.controller;

import br.com.gerenciador.dto.input.EnderecoCadastroRequest;
import br.com.gerenciador.dto.output.EnderecoResponse;
import br.com.gerenciador.mapper.PessoaMapperCadastro;
import br.com.gerenciador.models.EnderecoModel;
import br.com.gerenciador.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoas")
public class EnderecoControllerPost {
    private PessoaService service;
    private PessoaMapperCadastro mapper;

    @Autowired
    public EnderecoControllerPost(PessoaService service, PessoaMapperCadastro mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/{id}/endereco")
    public ResponseEntity<EnderecoResponse> cadastrarEndereco(
            @PathVariable Long id,
            @RequestBody @Validated EnderecoCadastroRequest request
    ) {
        EnderecoModel enderecoModel = mapper.convertToModel(request, null);
        EnderecoModel responseModel = service.criarEndereco(id, enderecoModel);

        if (responseModel != null && responseModel.getId() != null){
            EnderecoResponse response = mapper.convertToResponse(responseModel);

            return new ResponseEntity<EnderecoResponse>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<EnderecoResponse>(HttpStatus.BAD_REQUEST);
    }
}
