package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.modelos.MercadoriaAtualizador;
import com.autobots.automanager.modelos.MercadoriaSelecionador;
import com.autobots.automanager.modelos.AdicionadorLinkMercadoria;
import com.autobots.automanager.repositorios.MercadoriaRepository;

@RestController
@RequestMapping("/mercadorias")
public class MercadoriaController {

    @Autowired
    private MercadoriaRepository repository;

    @Autowired
    private MercadoriaSelecionador selecionador;

    @Autowired
    private AdicionadorLinkMercadoria adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Mercadoria> obterMercadoria(@PathVariable long id) {
        List<Mercadoria> mercadorias = repository.findAll();
        Mercadoria mercadoria = selecionador.selecionar(mercadorias, id);
        if (mercadoria == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(mercadoria);
            return new ResponseEntity<>(mercadoria, HttpStatus.FOUND);
        }
    }

    @GetMapping("/mercadorias")
    public ResponseEntity<List<Mercadoria>> obterMercadorias() {
        List<Mercadoria> mercadorias = repository.findAll();
        if (mercadorias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(mercadorias);
            return new ResponseEntity<>(mercadorias, HttpStatus.FOUND);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> criarMercadoria(@RequestBody Mercadoria mercadoria) {
        if (mercadoria.getId() == null) {
            repository.save(mercadoria);
            adicionadorLink.adicionarLink(mercadoria);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarMercadoria(@RequestBody Mercadoria atualizacao) {
        Mercadoria mercadoria = repository.getById(atualizacao.getId());
        if (mercadoria != null) {
            MercadoriaAtualizador atualizador = new MercadoriaAtualizador();
            atualizador.atualizar(mercadoria, atualizacao);
            repository.save(mercadoria);
            adicionadorLink.adicionarLink(mercadoria);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirMercadoria(@RequestBody Mercadoria exclusao) {
        Mercadoria mercadoria = repository.getById(exclusao.getId());
        if (mercadoria != null) {
            repository.delete(mercadoria);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
