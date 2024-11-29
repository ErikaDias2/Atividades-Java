package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVenda;

@RestController
@RequestMapping("/vendas")
public class ControleVenda {

    @Autowired
    private RepositorioVenda repositorioVenda;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    // ADMIN e GERENTE podem criar vendas para qualquer cliente
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarVenda(@RequestBody Venda venda) {
        repositorioVenda.save(venda);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // VENDEDOR pode criar vendas apenas para si mesmo
    @PreAuthorize("hasRole('VENDEDOR')")
    @PostMapping("/cadastrar-minha-venda")
    public ResponseEntity<?> cadastrarMinhaVenda(@RequestBody Venda venda) {
        String nomeUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario vendedor = repositorioUsuario.findByCredencialNomeUsuario(nomeUsuario);

        if (vendedor == null) {
            return new ResponseEntity<>("Vendedor não encontrado.", HttpStatus.NOT_FOUND);
        }

        venda.setFuncionario(vendedor); // Associa a venda ao vendedor logado
        repositorioVenda.save(venda);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // ADMIN e GERENTE podem atualizar qualquer venda
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarVenda(@PathVariable Long id, @RequestBody Venda vendaAtualizada) {
        return repositorioVenda.findById(id).map(venda -> {
            venda.setMercadorias(vendaAtualizada.getMercadorias());
            venda.setValorTotal(vendaAtualizada.getValorTotal());
            venda.setCliente(vendaAtualizada.getCliente());
            repositorioVenda.save(venda);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElse(new ResponseEntity<>("Venda não encontrada.", HttpStatus.NOT_FOUND));
    }

    // ADMIN e GERENTE podem deletar qualquer venda
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarVenda(@PathVariable Long id) {
        return repositorioVenda.findById(id).map(venda -> {
            repositorioVenda.delete(venda);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElse(new ResponseEntity<>("Venda não encontrada.", HttpStatus.NOT_FOUND));
    }

    // ADMIN, GERENTE e VENDEDOR podem listar todas as vendas
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @GetMapping("/listar")
    public ResponseEntity<List<Venda>> listarVendas() {
        return new ResponseEntity<>(repositorioVenda.findAll(), HttpStatus.OK);
    }

    // VENDEDOR pode ver apenas suas próprias vendas
    @PreAuthorize("hasRole('VENDEDOR')")
    @GetMapping("/minhas-vendas")
    public ResponseEntity<List<Venda>> listarMinhasVendas() {
        String nomeUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario vendedor = repositorioUsuario.findByCredencialNomeUsuario(nomeUsuario);

        if (vendedor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Venda> vendas = repositorioVenda.findByFuncionario(vendedor);
        return new ResponseEntity<>(vendas, HttpStatus.OK);
    }
}
