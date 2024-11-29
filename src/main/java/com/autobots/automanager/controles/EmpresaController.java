package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.modelos.EmpresaAtualizador;
import com.autobots.automanager.modelos.EmpresaSelecionador;
import com.autobots.automanager.modelos.AdicionadorLinkEmpresa;
import com.autobots.automanager.repositorios.EmpresaRepository;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaRepository repository;

    @Autowired
    private EmpresaSelecionador selecionador;

    @Autowired
    private AdicionadorLinkEmpresa adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obterEmpresa(@PathVariable long id) {
        List<Empresa> empresas = repository.findAll();
        Empresa empresa = selecionador.selecionar(empresas, id);
        if (empresa == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(empresa);
            return new ResponseEntity<>(empresa, HttpStatus.FOUND);
        }
    }

    @GetMapping("/empresas")
    public ResponseEntity<List<Empresa>> obterEmpresas() {
        List<Empresa> empresas = repository.findAll();
        if (empresas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(empresas);
            return new ResponseEntity<>(empresas, HttpStatus.FOUND);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> criarEmpresa(@RequestBody Empresa empresa) {
        if (empresa.getId() == null) {
            repository.save(empresa);
            adicionadorLink.adicionarLink(empresa);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarEmpresa(@RequestBody Empresa atualizacao) {
        Empresa empresa = repository.getById(atualizacao.getId());
        if (empresa != null) {
            EmpresaAtualizador atualizador = new EmpresaAtualizador();
            atualizador.atualizar(empresa, atualizacao);
            repository.save(empresa);
            adicionadorLink.adicionarLink(empresa);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirEmpresa(@RequestBody Empresa exclusao) {
        Empresa empresa = repository.getById(exclusao.getId());
        if (empresa != null) {
            repository.delete(empresa);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}