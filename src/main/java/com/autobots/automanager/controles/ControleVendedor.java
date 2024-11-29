package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelos.Perfil;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@RestController
@RequestMapping("/vendedores")
public class ControleVendedor {

    @Autowired
    private RepositorioUsuario repositorio;

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarVendedor(@RequestBody Usuario vendedor) {
        try {
            BCryptPasswordEncoder codificador = new BCryptPasswordEncoder();
            Credencial credencial = new Credencial();
            credencial.setNomeUsuario(vendedor.getCredencial().getNomeUsuario());
            credencial.setSenha(codificador.encode(vendedor.getCredencial().getSenha()));
            vendedor.setCredencial(credencial);
            vendedor.getPerfis().add(Perfil.ROLE_VENDEDOR); // Definindo perfil como VENDEDOR
            repositorio.save(vendedor);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao cadastrar vendedor.", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarVendedor(@PathVariable Long id, @RequestBody Usuario vendedorAtualizado) {
        return repositorio.findById(id).map(vendedor -> {
            if (!vendedor.getPerfis().contains(Perfil.ROLE_VENDEDOR)) {
                return new ResponseEntity<>("O usuário não é um vendedor.", HttpStatus.FORBIDDEN);
            }
            vendedor.setNome(vendedorAtualizado.getNome());
            repositorio.save(vendedor);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElse(new ResponseEntity<>("Vendedor não encontrado.", HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarVendedor(@PathVariable Long id) {
        return repositorio.findById(id).map(vendedor -> {
            if (!vendedor.getPerfis().contains(Perfil.ROLE_VENDEDOR)) {
                return new ResponseEntity<>("O usuário não é um vendedor.", HttpStatus.FORBIDDEN);
            }
            repositorio.delete(vendedor);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElse(new ResponseEntity<>("Vendedor não encontrado.", HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarVendedores() {
        List<Usuario> vendedores = repositorio.findAll().stream()
                .filter(usuario -> usuario.getPerfis().contains(Perfil.ROLE_VENDEDOR))
                .toList();
        return new ResponseEntity<>(vendedores, HttpStatus.OK);
    }
}
