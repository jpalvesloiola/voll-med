package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;

    /**
     * Cadastra uma entidade no Banco de Dados passada no corpo da requisição POST e retorna o detalhamento da entidade
     * cadastrada assim como a URI gerada para acessar o detalhamento
     * @param dados
     * Parametro passado automaticamente pelo cliente(navegador) permitindo a construção de uma URI de acesso à entidade
     * @param uriBuilder
     * @return HTTP Código 201
     */
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
        Medico medico = new Medico(dados);
        repository.save(medico);
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    /**
     * Lista as entidades cadastradas de acordo com os parametros de tamanho e ordenação da API de
     * paginação @PageableDefault(size = x, sort = `{"atributo"})
     * @param paginacao
     * @return HTTP Código 200
     */
    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 20, sort = {"nome"}) Pageable paginacao){
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    /**
     * Atualiza entidade cadastrada no Banco de Dados passando o ID da entidade no corpo da
     * requisição e os atributos que serão atualizados
     * @param dados
     * @return HTTP Código 200
     */
    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizarMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    /**
     * Aplica a exclusão lógica da entidade cadastrada no Banco de Dados
     * @param id
     * @return HTTP Código 204
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.excluir();
        return ResponseEntity.noContent().build();
    }

    /**
     * Retorna o detalhamento da entidade cadastrada, passando o id como caminho na URL
     * @param id
     * @return HTTP Código 200
     */
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity detalhar(@PathVariable Long id){
            var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
}
