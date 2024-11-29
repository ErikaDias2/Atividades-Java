package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.modelos.VeiculoAtualizador;
import com.autobots.automanager.modelos.VeiculoSelecionador;
import com.autobots.automanager.modelos.AdicionadorLinkVeiculo;
import com.autobots.automanager.repositorios.VeiculoRepository;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoRepository repository;

    @Autowired
    private VeiculoSelecionador selecionador;

    @Autowired
    private AdicionadorLinkVeiculo adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> obterVeiculo(@PathVariable long id) {
        List<Veiculo> veiculos = repository.findAll();
        Veiculo veiculo = selecionador.selecionar(veiculos, id);
        if (veiculo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(veiculo);
            return new ResponseEntity<>(veiculo, HttpStatus.FOUND);
        }
    }

    @GetMapping("/veiculos")
    public ResponseEntity<List<Veiculo>> obterVeiculos() {
        List<Veiculo> veiculos = repository.findAll();
        if (veiculos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(veiculos);
            return new ResponseEntity<>(veiculos, HttpStatus.FOUND);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> criarVeiculo(@RequestBody Veiculo veiculo) {
        if (veiculo.getId() == null) {
            repository.save(veiculo);
            adicionadorLink.adicionarLink(veiculo);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarVeiculo(@RequestBody Veiculo atualizacao) {
        Veiculo veiculo = repository.getById(atualizacao.getId());
        if (veiculo != null) {
            VeiculoAtualizador atualizador = new VeiculoAtualizador();
            atualizador.atualizar(veiculo, atualizacao);
            repository.save(veiculo);
            adicionadorLink.adicionarLink(veiculo);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirVeiculo(@RequestBody Veiculo exclusao) {
        Veiculo veiculo = repository.getById(exclusao.getId());
        if (veiculo != null) {
            repository.delete(veiculo);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}