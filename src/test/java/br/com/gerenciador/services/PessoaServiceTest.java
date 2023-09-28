package br.com.gerenciador.services;

import br.com.gerenciador.enums.TipoLogradouro;
import br.com.gerenciador.models.EnderecoModel;
import br.com.gerenciador.models.PessoaModel;
import br.com.gerenciador.repository.EnderecoRepository;
import br.com.gerenciador.repository.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PessoaServiceTest {
    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Test
    void cadastrar_pessoa() {
        PessoaModel pessoa = new PessoaModel("Rafael", LocalDate.of(1994, 2, 28));
        PessoaModel pessoaCadastrada = new PessoaModel(1L,"Rafael", LocalDate.of(1994, 2, 28));

        when(pessoaRepository.save(pessoa)).thenReturn(pessoaCadastrada);

        PessoaModel result = pessoaService.cadastrarPessoa(pessoa);

        assertNotNull(result);
        assertEquals(1L,result.getId());
        assertEquals(pessoa.getNome(), result.getNome());
        assertEquals(pessoa.getDataNascimento(), result.getDataNascimento());
    }

    @Test
    void editar_nome_pessoa_resultado_true() {
        PessoaModel pessoa = new PessoaModel(1L,"Eduardo", LocalDate.of(1995, 2, 28));
        PessoaModel pessoaCadastrada = new PessoaModel(1L,"Rafael", LocalDate.of(1994, 2, 28));

        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoaCadastrada));

        boolean result = pessoaService.editarPessoa(pessoa);

        assertTrue(result);
    }

    @Test
    void editar_nascimento_pessoa_resultado_true() {
        PessoaModel pessoa = new PessoaModel(1L,"Rafael", LocalDate.of(1995, 2, 27));
        PessoaModel pessoaCadastrada = new PessoaModel(1L,"Rafael", LocalDate.of(1994, 2, 28));

        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoaCadastrada));

        boolean result = pessoaService.editarPessoa(pessoa);

        assertTrue(result);
    }

    @Test
    void editar_pessoa_nao_encontrada() {
        PessoaModel pessoa = new PessoaModel(2L,"Eduardo", LocalDate.of(1995, 2, 28));
        when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = pessoaService.editarPessoa(pessoa);

        assertFalse(result);
    }

    @Test
    void consultar_pessoa() {
        Long id = 1L;

        PessoaModel pessoa = new PessoaModel(1L,"Rafael", LocalDate.of(1994, 2, 28));
        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));

        PessoaModel result = pessoaService.consultarPessoa(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(pessoa.getNome(), result.getNome());
        assertEquals(pessoa.getDataNascimento(), result.getDataNascimento());
    }

    @Test
    void listar_pessoas() {
        PessoaModel pessoa1 = new PessoaModel("Rafael", LocalDate.of(1994, 2, 28));
        PessoaModel pessoa2 = new PessoaModel("Eduardo", LocalDate.of(1995, 3, 15));

        List<PessoaModel> pessoas = new ArrayList<>();
        pessoas.add(pessoa1);
        pessoas.add(pessoa2);

        when(pessoaRepository.findAll()).thenReturn(pessoas);

        List<PessoaModel> result = pessoaService.listarPessoas();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(pessoa1.getNome(), result.get(0).getNome());
        assertEquals(pessoa2.getNome(), result.get(1).getNome());
    }

    @Test
    void criar_endereco_sucesso() {
        Long pessoaId = 1L;

        PessoaModel pessoa = new PessoaModel(1L, "Rafael", LocalDate.of(1994, 2, 28));
        EnderecoModel endereco = new EnderecoModel(TipoLogradouro.RUA, "12345-678", "123", "Presidente Prudente", true, pessoa);
        EnderecoModel enderecoCadastrado = new EnderecoModel(1L, TipoLogradouro.RUA, "12345-678", "123", "Presidente Prudente", true, pessoa);

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoa));
        when(enderecoRepository.save(endereco)).thenReturn(enderecoCadastrado);

        EnderecoModel result = pessoaService.criarEndereco(pessoaId, endereco);

        assertNotNull(result);
        assertEquals(endereco, result);
        assertTrue(pessoa.getEnderecos().contains(endereco));
    }

    @Test
    void criar_endereco_falha() {
        Long pessoaId = 1L;

        PessoaModel pessoa = new PessoaModel(2L,"Rafael", LocalDate.of(1994, 2, 28));
        EnderecoModel endereco = new EnderecoModel(TipoLogradouro.RUA, "12345-678", "123", "Presidente Prudente", true, pessoa);
        EnderecoModel enderecoCadastrado = new EnderecoModel(1L, TipoLogradouro.RUA, "12345-678", "123", "Presidente Prudente", true, pessoa);

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.empty());
        when(enderecoRepository.save(endereco)).thenReturn(enderecoCadastrado);

        EnderecoModel result = pessoaService.criarEndereco(pessoaId, endereco);

        assertNull(result);
    }

    @Test
    void listar_enderecos() {
        Long pessoaId = 1L;

        PessoaModel pessoa = new PessoaModel("Rafael", LocalDate.of(1994, 2, 28));
        EnderecoModel endereco1 = new EnderecoModel(TipoLogradouro.RUA, "12345-678", "123", "Presidente Prudente", true, pessoa);
        EnderecoModel endereco2 = new EnderecoModel(TipoLogradouro.AVENIDA, "54321-876", "456", "Maringa", false, pessoa);
        pessoa.addEndereco(endereco1);
        pessoa.addEndereco(endereco2);

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoa));

        Set<EnderecoModel> result = pessoaService.listarEnderecos(pessoaId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(endereco1));
        assertTrue(result.contains(endereco2));
    }

    @Test
    void listar_enderecos_usuario_nao_encontrado() {
        Long pessoaId = 1L;

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.empty());

        Set<EnderecoModel> result = pessoaService.listarEnderecos(pessoaId);

        assertEquals(Collections.emptySet(),result);
    }

    @Test
    void cadastrar_endereco_principal_sucesso() {
        Long pessoaId = 1L;
        Long enderecoId = 2L;
        PessoaModel pessoa = new PessoaModel(1L,"Rafael", LocalDate.of(1994, 2, 28));
        EnderecoModel endereco1 = new EnderecoModel(1L,TipoLogradouro.RUA, "12345-678", "123", "Presidente Prudente", true, pessoa);
        EnderecoModel endereco2 = new EnderecoModel(2L, TipoLogradouro.AVENIDA, "54321-876", "456", "Curitiba", false, pessoa);

        pessoa.addEndereco(endereco1);
        pessoa.addEndereco(endereco2);

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoa));
        when(enderecoRepository.findById(enderecoId)).thenReturn(Optional.of(endereco2));

        boolean result = pessoaService.cadastrarEnderecoPrincipal(pessoaId, enderecoId);

        assertTrue(result);
        assertTrue(endereco2.isEnderecoPrincipal());
        assertFalse(endereco1.isEnderecoPrincipal());
    }

    @Test
    void cadastrar_endereco_principal_falha() {
        Long pessoaId = 1L;
        Long enderecoId = 3L;
        PessoaModel pessoa = new PessoaModel(1L,"Rafael", LocalDate.of(1994, 2, 28));
        EnderecoModel endereco1 = new EnderecoModel(1L,TipoLogradouro.RUA, "12345-678", "123", "Presidente Prudente", true, pessoa);
        EnderecoModel endereco2 = new EnderecoModel(2L, TipoLogradouro.AVENIDA, "54321-876", "456", "Curitiba", false, pessoa);

        pessoa.addEndereco(endereco1);
        pessoa.addEndereco(endereco2);

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoa));
        when(enderecoRepository.findById(enderecoId)).thenReturn(Optional.empty());

        boolean result = pessoaService.cadastrarEnderecoPrincipal(pessoaId, enderecoId);

        assertFalse(result);
    }
}