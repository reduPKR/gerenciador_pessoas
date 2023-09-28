package br.com.gerenciador.controller;

import br.com.gerenciador.dto.input.PessoaAtualizarRequest;
import br.com.gerenciador.dto.output.PessoaAtualizarResponse;
import br.com.gerenciador.mapper.PessoaMapperAtualizar;
import br.com.gerenciador.mapper.PessoaMapperCadastro;
import br.com.gerenciador.models.PessoaModel;
import br.com.gerenciador.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoas")
public class PessoaControllerPut {
    private PessoaService pessoaService;
    private PessoaMapperAtualizar pessoaMapper;

    @Autowired
    public PessoaControllerPut(PessoaService pessoaService, PessoaMapperAtualizar pessoaMapperCadastro) {
        this.pessoaService = pessoaService;
        this.pessoaMapper = pessoaMapperCadastro;
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaAtualizarResponse> atualizarPessoa(
            @PathVariable Long id,
            @RequestBody @Validated PessoaAtualizarRequest request
    ) {
        PessoaModel pessoaModel = pessoaMapper.convertToModel(request);
        pessoaModel.setId(id);

        Boolean atualizado = pessoaService.editarPessoa(pessoaModel);

        if (atualizado){
            PessoaAtualizarResponse response = pessoaMapper.convertToResponse(pessoaModel);

            return new ResponseEntity<PessoaAtualizarResponse>(response, HttpStatus.OK);
        }

        return new ResponseEntity<PessoaAtualizarResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

