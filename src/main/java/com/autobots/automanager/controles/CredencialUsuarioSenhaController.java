package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.entitades.CredencialUsuarioSenha;
import com.autobots.automanager.modelos.CredencialUsuarioSenhaAtualizador;
import com.autobots.automanager.modelos.CredencialUsuarioSenhaSelecionador;
import com.autobots.automanager.modelos.AdicionadorLinkCredencialUsuarioSenha;
import com.autobots.automanager.repositorios.CredencialUsuarioSenhaRepository;

@RestController
@RequestMapping("/credenciais-usuario-senha")
public class CredencialUsuarioSenhaController {

    @Autowired
    private CredencialUsuarioSenhaRepository repository;

    @Autowired
    private CredencialUsuarioSenhaSelecionador selecionador;

    @Autowired
    private AdicionadorLinkCredencialUsuarioSenha adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<CredencialUsuarioSenha> obterCredencial(@PathVariable long id) {
        List<CredencialUsuarioSenha> credenciais = repository.findAll();
        CredencialUsuarioSenha credencial = selecionador.selecionar(credenciais, id);
        if (credencial == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(credencial);
            return new ResponseEntity<>(credencial, HttpStatus.FOUND);
        }
    }

    @GetMapping("/credenciais")
    public ResponseEntity<List<CredencialUsuarioSenha>> obterCredenciais() {
        List<CredencialUsuarioSenha> credenciais = repository.findAll();
        if (credenciais.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(credenciais);
            return new ResponseEntity<>(credenciais, HttpStatus.FOUND);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> criarCredencial(@RequestBody CredencialUsuarioSenha credencial) {
        if (credencial.getId() == null) {
            repository.save(credencial);
            adicionadorLink.adicionarLink(credencial);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarCredencial(@RequestBody CredencialUsuarioSenha atualizacao) {
        CredencialUsuarioSenha credencial = repository.getById(atualizacao.getId());
        if (credencial != null) {
            CredencialUsuarioSenhaAtualizador atualizador = new CredencialUsuarioSenhaAtualizador();
            atualizador.atualizar(credencial, atualizacao);
            repository.save(credencial);
            adicionadorLink.adicionarLink(credencial);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirCredencial(@RequestBody CredencialUsuarioSenha exclusao) {
        CredencialUsuarioSenha credencial = repository.getById(exclusao.getId());
        if (credencial != null) {
            repository.delete(credencial);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}