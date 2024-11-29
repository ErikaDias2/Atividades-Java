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
@RequestMapping("/administradores")
public class ControleAdministrador {

    @Autowired
    private RepositorioUsuario repositorio;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarAdministrador(@RequestBody Usuario administrador) {
        try {
            BCryptPasswordEncoder codificador = new BCryptPasswordEncoder();
            Credencial credencial = new Credencial();
            credencial.setNomeUsuario(administrador.getCredencial().getNomeUsuario());
            credencial.setSenha(codificador.encode(administrador.getCredencial().getSenha()));
            administrador.setCredencial(credencial);
            administrador.getPerfis().add(Perfil.ROLE_ADMIN);
            repositorio.save(administrador);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao cadastrar administrador.", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarAdministrador(@PathVariable Long id, @RequestBody Usuario administradorAtualizado) {
        return repositorio.findById(id).map(administrador -> {
            if (!administrador.getPerfis().contains(Perfil.ROLE_ADMIN)) {
                return new ResponseEntity<>("O usuário não é um administrador.", HttpStatus.FORBIDDEN);
            }
            administrador.setNome(administradorAtualizado.getNome());
            repositorio.save(administrador);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElse(new ResponseEntity<>("Administrador não encontrado.", HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarAdministrador(@PathVariable Long id) {
        return repositorio.findById(id).map(administrador -> {
            if (!administrador.getPerfis().contains(Perfil.ROLE_ADMIN)) {
                return new ResponseEntity<>("O usuário não é um administrador.", HttpStatus.FORBIDDEN);
            }
            repositorio.delete(administrador);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElse(new ResponseEntity<>("Administrador não encontrado.", HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarAdministradores() {
        List<Usuario> administradores = repositorio.findAll().stream()
                .filter(usuario -> usuario.getPerfis().contains(Perfil.ROLE_ADMIN))
                .toList();
        return new ResponseEntity<>(administradores, HttpStatus.OK);
    }
}
