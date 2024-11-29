package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.modelos.ServicoAtualizador;
import com.autobots.automanager.modelos.ServicoSelecionador;
import com.autobots.automanager.modelos.AdicionadorLinkServico;
import com.autobots.automanager.repositorios.ServicoRepository;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository repository;

    @Autowired
    private ServicoSelecionador selecionador;

    @Autowired
    private AdicionadorLinkServico adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Servico> obterServico(@PathVariable long id) {
        List<Servico> servicos = repository.findAll();
        Servico servico = selecionador.selecionar(servicos, id);
        if (servico == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(servico);
            return new ResponseEntity<>(servico, HttpStatus.FOUND);
        }
    }

    @GetMapping("/servicos")
    public ResponseEntity<List<Servico>> obterServicos() {
        List<Servico> servicos = repository.findAll();
        if (servicos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(servicos);
            return new ResponseEntity<>(servicos, HttpStatus.FOUND);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> criarServico(@RequestBody Servico servico) {
        if (servico.getId() == null) {
            repository.save(servico);
            adicionadorLink.adicionarLink(servico);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarServico(@RequestBody Servico atualizacao) {
        Servico servico = repository.getById(atualizacao.getId());
        if (servico != null) {
            ServicoAtualizador atualizador = new ServicoAtualizador();
            atualizador.atualizar(servico, atualizacao);
            repository.save(servico);
            adicionadorLink.adicionarLink(servico);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirServico(@RequestBody Servico exclusao) {
        Servico servico = repository.getById(exclusao.getId());
        if (servico != null) {
            repository.delete(servico);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
