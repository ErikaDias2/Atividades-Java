package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.repositorios.RepositorioMercadoria;

@RestController
@RequestMapping("/mercadorias")
public class ControleMercadoria {

    @Autowired
    private RepositorioMercadoria repositorio;

    // ADMIN e GERENTE podem criar mercadorias
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarMercadoria(@RequestBody Mercadoria mercadoria) {
        repositorio.save(mercadoria);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // ADMIN e GERENTE podem atualizar mercadorias
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarMercadoria(@PathVariable Long id, @RequestBody Mercadoria mercadoriaAtualizada) {
        return repositorio.findById(id).map(mercadoria -> {
            mercadoria.setDescricao(mercadoriaAtualizada.getDescricao());
            mercadoria.setValor(mercadoriaAtualizada.getValor());
            repositorio.save(mercadoria);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElse(new ResponseEntity<>("Mercadoria não encontrada.", HttpStatus.NOT_FOUND));
    }

    // ADMIN e GERENTE podem deletar mercadorias
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarMercadoria(@PathVariable Long id) {
        return repositorio.findById(id).map(mercadoria -> {
            repositorio.delete(mercadoria);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElse(new ResponseEntity<>("Mercadoria não encontrada.", HttpStatus.NOT_FOUND));
    }

    // ADMIN, GERENTE e VENDEDOR podem listar todas as mercadorias
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @GetMapping("/listar")
    public ResponseEntity<List<Mercadoria>> listarMercadorias() {
        return new ResponseEntity<>(repositorio.findAll(), HttpStatus.OK);
    }
}
