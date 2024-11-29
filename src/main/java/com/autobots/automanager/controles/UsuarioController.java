package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.modelos.UsuarioAtualizador;
import com.autobots.automanager.modelos.UsuarioSelecionador;
import com.autobots.automanager.modelos.AdicionadorLinkUsuario;
import com.autobots.automanager.repositorios.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioSelecionador selecionador;

    @Autowired
    private AdicionadorLinkUsuario adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obterUsuario(@PathVariable long id) {
        List<Usuario> usuarios = repository.findAll();
        Usuario usuario = selecionador.selecionar(usuarios, id);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(usuario);
            return new ResponseEntity<>(usuario, HttpStatus.FOUND);
        }
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> obterUsuarios() {
        List<Usuario> usuarios = repository.findAll();
        if (usuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(usuarios);
            return new ResponseEntity<>(usuarios, HttpStatus.FOUND);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario) {
        if (usuario.getId() == null) {
            repository.save(usuario);
            adicionadorLink.adicionarLink(usuario);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarUsuario(@RequestBody Usuario atualizacao) {
        Usuario usuario = repository.getById(atualizacao.getId());
        if (usuario != null) {
            UsuarioAtualizador atualizador = new UsuarioAtualizador();
            atualizador.atualizar(usuario, atualizacao);
            repository.save(usuario);
            adicionadorLink.adicionarLink(usuario);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirUsuario(@RequestBody Usuario exclusao) {
        Usuario usuario = repository.getById(exclusao.getId());
        if (usuario != null) {
            repository.delete(usuario);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}