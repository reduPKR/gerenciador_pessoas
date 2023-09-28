package br.com.gerenciador.controller;

import br.com.gerenciador.dto.input.PessoaCadastroRequest;
import br.com.gerenciador.dto.output.PessoaCadastroResponse;
import br.com.gerenciador.mapper.PessoaMapper;
import br.com.gerenciador.models.PessoaModel;
import br.com.gerenciador.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pessoas")
public class PessoaControllerPost {
    private PessoaService service;
    private PessoaMapper mapper;

    @Autowired
    public PessoaControllerPost(PessoaService service, PessoaMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<PessoaCadastroResponse> cadastrarPessoa(@RequestBody @Validated PessoaCadastroRequest request) {
        PessoaModel pessoa = mapper.convertToModel(request);
        PessoaModel responseModel = service.cadastrarPessoa(pessoa);

        if (responseModel != null && responseModel.getId() != null){
            PessoaCadastroResponse response = mapper.convertToResponse(responseModel);

            return new ResponseEntity<PessoaCadastroResponse>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<PessoaCadastroResponse>(HttpStatus.BAD_REQUEST);
    }
}