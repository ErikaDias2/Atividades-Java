package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.entitades.Email;
import com.autobots.automanager.modelos.EmailAtualizador;
import com.autobots.automanager.modelos.EmailSelecionador;
import com.autobots.automanager.modelos.AdicionadorLinkEmail;
import com.autobots.automanager.repositorios.EmailRepository;

@RestController
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private EmailRepository repository;

    @Autowired
    private EmailSelecionador selecionador;

    @Autowired
    private AdicionadorLinkEmail adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Email> obterEmail(@PathVariable long id) {
        List<Email> emails = repository.findAll();
        Email email = selecionador.selecionar(emails, id);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(email);
            return new ResponseEntity<>(email, HttpStatus.FOUND);
        }
    }

    @GetMapping("/emails")
    public ResponseEntity<List<Email>> obterEmails() {
        List<Email> emails = repository.findAll();
        if (emails.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(emails);
            return new ResponseEntity<>(emails, HttpStatus.FOUND);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> criarEmail(@RequestBody Email email) {
        if (email.getId() == null) {
            repository.save(email);
            adicionadorLink.adicionarLink(email);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarEmail(@RequestBody Email atualizacao) {
        Email email = repository.getById(atualizacao.getId());
        if (email != null) {
            EmailAtualizador atualizador = new EmailAtualizador();
            atualizador.atualizar(email, atualizacao);
            repository.save(email);
            adicionadorLink.adicionarLink(email);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirEmail(@RequestBody Email exclusao) {
        Email email = repository.getById(exclusao.getId());
        if (email != null) {
            repository.delete(email);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}