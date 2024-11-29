package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.entitades.CredencialCodigoBarra;
import com.autobots.automanager.modelos.AdicionadorLinkCredencialCodigoBarra;
import com.autobots.automanager.modelos.CredencialCodigoBarraAtualizador;
import com.autobots.automanager.modelos.CredencialCodigoBarraSelecionador;
import com.autobots.automanager.repositorios.CredencialCodigoBarraRepository;

@RestController
@RequestMapping("/credenciais-codigo-barra")
public class CredencialCodigoBarraController {

    @Autowired
    private CredencialCodigoBarraRepository repository;

    @Autowired
    private CredencialCodigoBarraSelecionador selecionador;

    @Autowired
    private AdicionadorLinkCredencialCodigoBarra adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<CredencialCodigoBarra> obterCredencial(@PathVariable long id) {
        List<CredencialCodigoBarra> credenciais = repository.findAll();
        CredencialCodigoBarra credencial = selecionador.selecionar(credenciais, id);
        if (credencial == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(credencial);
            return new ResponseEntity<>(credencial, HttpStatus.FOUND);
        }
    }

    @GetMapping("/credenciais")
    public ResponseEntity<List<CredencialCodigoBarra>> obterCredenciais() {
        List<CredencialCodigoBarra> credenciais = repository.findAll();
        if (credenciais.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(credenciais);
            return new ResponseEntity<>(credenciais, HttpStatus.FOUND);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> criarCredencial(@RequestBody CredencialCodigoBarra credencial) {
        if (credencial.getId() == null) {
            repository.save(credencial);
            adicionadorLink.adicionarLink(credencial);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarCredencial(@RequestBody CredencialCodigoBarra atualizacao) {
        CredencialCodigoBarra credencial = repository.getById(atualizacao.getId());
        if (credencial != null) {
            CredencialCodigoBarraAtualizador atualizador = new CredencialCodigoBarraAtualizador();
            atualizador.atualizar(credencial, atualizacao);
            repository.save(credencial);
            adicionadorLink.adicionarLink(credencial);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirCredencial(@RequestBody CredencialCodigoBarra exclusao) {
        CredencialCodigoBarra credencial = repository.getById(exclusao.getId());
        if (credencial != null) {
            repository.delete(credencial);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}