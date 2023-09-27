package br.com.gerenciador.repository;

import br.com.gerenciador.models.PessoaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<PessoaModel, Long>{
}
