package br.com.gerenciador.controller;

import br.com.gerenciador.dto.output.PessoaVisualizacaoResponse;
import br.com.gerenciador.mapper.PessoaMapperVisualizacao;
import br.com.gerenciador.models.PessoaModel;
import br.com.gerenciador.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pessoas")
public class PessoaControllerGet {
    private PessoaService pessoaService;
    private PessoaMapperVisualizacao pessoaMapper;

    @Autowired
    public PessoaControllerGet(PessoaService pessoaService, PessoaMapperVisualizacao pessoaMapper) {
        this.pessoaService = pessoaService;
        this.pessoaMapper = pessoaMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaVisualizacaoResponse> getPessoaById(@PathVariable Long id) {
        PessoaModel pessoaModel = pessoaService.consultarPessoa(id);

        if (pessoaModel != null) {
            PessoaVisualizacaoResponse response = pessoaMapper.convertToResponse(pessoaModel);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}

