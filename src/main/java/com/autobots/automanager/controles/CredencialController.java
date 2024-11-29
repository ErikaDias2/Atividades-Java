package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.entitades.Credencial;
import com.autobots.automanager.modelos.CredencialAtualizador;
import com.autobots.automanager.modelos.CredencialSelecionador;
import com.autobots.automanager.modelos.AdicionadorLinkCredencial;
import com.autobots.automanager.repositorios.CredencialRepository;

@RestController
@RequestMapping("/credenciais")
public class CredencialController {

    @Autowired
    private CredencialRepository repository;

    @Autowired
    private CredencialSelecionador selecionador;

    @Autowired
    private AdicionadorLinkCredencial adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Credencial> obterCredencial(@PathVariable long id) {
        List<Credencial> credenciais = repository.findAll();
        Credencial credencial = selecionador.selecionar(credenciais, id);
        if (credencial == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(credencial);
            return new ResponseEntity<>(credencial, HttpStatus.FOUND);
        }
    }

    @GetMapping("/credenciais")
    public ResponseEntity<List<Credencial>> obterCredenciais() {
        List<Credencial> credenciais = repository.findAll();
        if (credenciais.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(credenciais);
            return new ResponseEntity<>(credenciais, HttpStatus.FOUND);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> criarCredencial(@RequestBody Credencial credencial) {
        if (credencial.getId() == null) {
            repository.save(credencial);
            adicionadorLink.adicionarLink(credencial);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarCredencial(@RequestBody Credencial atualizacao) {
        Credencial credencial = repository.getById(atualizacao.getId());
        if (credencial != null) {
            CredencialAtualizador atualizador = new CredencialAtualizador();
            atualizador.atualizar(credencial, atualizacao);
            repository.save(credencial);
            adicionadorLink.adicionarLink(credencial);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirCredencial(@RequestBody Credencial exclusao) {
        Credencial credencial = repository.getById(exclusao.getId());
        if (credencial != null) {
            repository.delete(credencial);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}