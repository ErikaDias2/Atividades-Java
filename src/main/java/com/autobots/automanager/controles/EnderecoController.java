package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.entitades.Endereco;
import com.autobots.automanager.modelos.EnderecoAtualizador;
import com.autobots.automanager.modelos.EnderecoSelecionador;
import com.autobots.automanager.modelos.AdicionadorLinkEndereco;
import com.autobots.automanager.repositorios.EnderecoRepository;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoRepository repository;

    @Autowired
    private EnderecoSelecionador selecionador;

    @Autowired
    private AdicionadorLinkEndereco adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> obterEndereco(@PathVariable long id) {
        List<Endereco> enderecos = repository.findAll();
        Endereco endereco = selecionador.selecionar(enderecos, id);
        if (endereco == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(endereco);
            return new ResponseEntity<>(endereco, HttpStatus.FOUND);
        }
    }

    @GetMapping("/enderecos")
    public ResponseEntity<List<Endereco>> obterEnderecos() {
        List<Endereco> enderecos = repository.findAll();
        if (enderecos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(enderecos);
            return new ResponseEntity<>(enderecos, HttpStatus.FOUND);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> criarEndereco(@RequestBody Endereco endereco) {
        if (endereco.getId() == null) {
            repository.save(endereco);
            adicionadorLink.adicionarLink(endereco);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarEndereco(@RequestBody Endereco atualizacao) {
        Endereco endereco = repository.getById(atualizacao.getId());
        if (endereco != null) {
            EnderecoAtualizador atualizador = new EnderecoAtualizador();
            atualizador.atualizar(endereco, atualizacao);
            repository.save(endereco);
            adicionadorLink.adicionarLink(endereco);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirEndereco(@RequestBody Endereco exclusao) {
        Endereco endereco = repository.getById(exclusao.getId());
        if (endereco != null) {
            repository.delete(endereco);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}