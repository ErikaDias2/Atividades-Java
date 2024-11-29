package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.modelos.VendaAtualizador;
import com.autobots.automanager.modelos.VendaSelecionador;
import com.autobots.automanager.modelos.AdicionadorLinkVenda;
import com.autobots.automanager.repositorios.VendaRepository;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaRepository repository;

    @Autowired
    private VendaSelecionador selecionador;

    @Autowired
    private AdicionadorLinkVenda adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Venda> obterVenda(@PathVariable long id) {
        List<Venda> vendas = repository.findAll();
        Venda venda = selecionador.selecionar(vendas, id);
        if (venda == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(venda);
            return new ResponseEntity<>(venda, HttpStatus.FOUND);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Venda>> obterVendas() {
        List<Venda> vendas = repository.findAll();
        if (vendas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(vendas);
            return new ResponseEntity<>(vendas, HttpStatus.FOUND);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> criarVenda(@RequestBody Venda venda) {
        if (venda.getId() == null) {
            repository.save(venda);
            adicionadorLink.adicionarLink(venda);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarVenda(@RequestBody Venda atualizacao) {
        Venda venda = repository.getById(atualizacao.getId());
        if (venda != null) {
            VendaAtualizador atualizador = new VendaAtualizador();
            atualizador.atualizar(venda, atualizacao);
            repository.save(venda);
            adicionadorLink.adicionarLink(venda);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirVenda(@RequestBody Venda exclusao) {
        Venda venda = repository.getById(exclusao.getId());
        if (venda != null) {
            repository.delete(venda);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}