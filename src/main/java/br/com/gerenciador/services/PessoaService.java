package br.com.gerenciador.services;

import br.com.gerenciador.models.EnderecoModel;
import br.com.gerenciador.models.PessoaModel;
import br.com.gerenciador.repository.EnderecoRepository;
import br.com.gerenciador.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    public PessoaModel cadastrarPessoa(PessoaModel pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Boolean editarPessoa(Long id, PessoaModel pessoa) {
        PessoaModel pessoaModel = consultarPessoa(id);

        if(pessoaModel != null && (
                !Objects.equals(pessoaModel.getNome(), pessoa.getNome()) ||
                        pessoaModel.getDataNascimento() != pessoa.getDataNascimento()
                )
        ){
            pessoaRepository.save(pessoa);
            return true;
        }

        return false;
    }

    public PessoaModel consultarPessoa(Long id) {
        return pessoaRepository
                .findById(id)
                .orElse(null);
    }

    public List<PessoaModel> listarPessoas() {
        return pessoaRepository.findAll();
    }

    public EnderecoModel criarEndereco(Long pessoaId, EnderecoModel endereco) {
        PessoaModel pessoa = consultarPessoa(pessoaId);
        if (pessoa != null) {
            endereco.setPessoa(pessoa);

            if(pessoa.getEnderecos().isEmpty()){
                endereco.setEnderecoPrincipal(true);
            }

            pessoa.addEndereco(endereco);
            pessoaRepository.save(pessoa);
            return endereco;
        }
        return null;
    }

    public Set<EnderecoModel> listarEnderecos(Long pessoaId) {
        PessoaModel pessoa = consultarPessoa(pessoaId);
        if (pessoa != null) {
            return pessoa.getEnderecos();
        }
        return Collections.emptySet();
    }

    private EnderecoModel consultaEndereço(Long ederecoId){
        return enderecoRepository.findById(ederecoId).orElse(null);
    }

    public Boolean cadastrarEnderecoPrincipal(Long pessoaId,Long ederecoId) {
        PessoaModel pessoa = consultarPessoa(pessoaId);
        EnderecoModel endereco = consultaEndereço(ederecoId);

        if (
                pessoa != null &&
                endereco != null &&
                endereco.getPessoa() == pessoa
        ) {

            pessoa.getEnderecos()
                    .stream()
                    .forEach( item -> {
                            if (item.getId().equals(ederecoId)) {
                                item.setEnderecoPrincipal(true);
                            } else {
                                item.setEnderecoPrincipal(false);
                             }
                    });

            return true;
        }
        return false;
    }
}

