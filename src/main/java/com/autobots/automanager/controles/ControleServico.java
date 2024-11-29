package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.repositorios.RepositorioServico;

@RestController
@RequestMapping("/servicos")
public class ControleServico {

    @Autowired
    private RepositorioServico repositorio;

    // ADMIN e GERENTE podem criar serviços
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarServico(@RequestBody Servico servico) {
        repositorio.save(servico);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // ADMIN e GERENTE podem atualizar serviços
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarServico(@PathVariable Long id, @RequestBody Servico servicoAtualizado) {
        return repositorio.findById(id).map(servico -> {
            servico.setDescricao(servicoAtualizado.getDescricao());
            servico.setValor(servicoAtualizado.getValor());
            repositorio.save(servico);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElse(new ResponseEntity<>("Serviço não encontrado.", HttpStatus.NOT_FOUND));
    }

    // ADMIN e GERENTE podem deletar serviços
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarServico(@PathVariable Long id) {
        return repositorio.findById(id).map(servico -> {
            repositorio.delete(servico);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElse(new ResponseEntity<>("Serviço não encontrado.", HttpStatus.NOT_FOUND));
    }

    // ADMIN, GERENTE e VENDEDOR podem listar todos os serviços
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @GetMapping("/listar")
    public ResponseEntity<List<Servico>> listarServicos() {
        return new ResponseEntity<>(repositorio.findAll(), HttpStatus.OK);
    }
}
