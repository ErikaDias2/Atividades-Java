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
@RequestMapping("/clientes")
public class ControleCliente {

    @Autowired
    private RepositorioUsuario repositorio;

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarCliente(@RequestBody Usuario cliente) {
        try {
            BCryptPasswordEncoder codificador = new BCryptPasswordEncoder();
            Credencial credencial = new Credencial();
            credencial.setNomeUsuario(cliente.getCredencial().getNomeUsuario());
            credencial.setSenha(codificador.encode(cliente.getCredencial().getSenha()));
            cliente.setCredencial(credencial);
            cliente.getPerfis().add(Perfil.ROLE_CLIENTE); // Definindo perfil como CLIENTE
            repositorio.save(cliente);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao cadastrar cliente.", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarCliente(@PathVariable Long id, @RequestBody Usuario clienteAtualizado) {
        return repositorio.findById(id).map(cliente -> {
            if (!cliente.getPerfis().contains(Perfil.ROLE_CLIENTE)) {
                return new ResponseEntity<>("O usuário não é um cliente.", HttpStatus.FORBIDDEN);
            }
            cliente.setNome(clienteAtualizado.getNome());
            repositorio.save(cliente);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElse(new ResponseEntity<>("Cliente não encontrado.", HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarCliente(@PathVariable Long id) {
        return repositorio.findById(id).map(cliente -> {
            if (!cliente.getPerfis().contains(Perfil.ROLE_CLIENTE)) {
                return new ResponseEntity<>("O usuário não é um cliente.", HttpStatus.FORBIDDEN);
            }
            repositorio.delete(cliente);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElse(new ResponseEntity<>("Cliente não encontrado.", HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarClientes() {
        List<Usuario> clientes = repositorio.findAll().stream()
                .filter(usuario -> usuario.getPerfis().contains(Perfil.ROLE_CLIENTE))
                .toList();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }
}
