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
@RequestMapping("/gerentes")
public class ControleGerente {

    @Autowired
    private RepositorioUsuario repositorio;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarGerente(@RequestBody Usuario gerente) {
        try {
            BCryptPasswordEncoder codificador = new BCryptPasswordEncoder();
            Credencial credencial = new Credencial();
            credencial.setNomeUsuario(gerente.getCredencial().getNomeUsuario());
            credencial.setSenha(codificador.encode(gerente.getCredencial().getSenha()));
            gerente.setCredencial(credencial);
            gerente.getPerfis().add(Perfil.ROLE_GERENTE); // Definindo perfil como GERENTE
            repositorio.save(gerente);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao cadastrar gerente.", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarGerente(@PathVariable Long id, @RequestBody Usuario gerenteAtualizado) {
        return repositorio.findById(id).map(gerente -> {
            if (!gerente.getPerfis().contains(Perfil.ROLE_GERENTE)) {
                return new ResponseEntity<>("O usuário não é um gerente.", HttpStatus.FORBIDDEN);
            }
            gerente.setNome(gerenteAtualizado.getNome());
            repositorio.save(gerente);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElse(new ResponseEntity<>("Gerente não encontrado.", HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarGerente(@PathVariable Long id) {
        return repositorio.findById(id).map(gerente -> {
            if (!gerente.getPerfis().contains(Perfil.ROLE_GERENTE)) {
                return new ResponseEntity<>("O usuário não é um gerente.", HttpStatus.FORBIDDEN);
            }
            repositorio.delete(gerente);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElse(new ResponseEntity<>("Gerente não encontrado.", HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarGerentes() {
        List<Usuario> gerentes = repositorio.findAll().stream()
                .filter(usuario -> usuario.getPerfis().contains(Perfil.ROLE_GERENTE))
                .toList();
        return new ResponseEntity<>(gerentes, HttpStatus.OK);
    }
}
