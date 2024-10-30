package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.EnderecoAtualizador;
import com.autobots.automanager.modelo.EnderecoSelecionador;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
    @Autowired
    private EnderecoRepositorio repositorio;

    @Autowired
    private EnderecoSelecionador selecionador;

    @GetMapping("/{id}")
    public Endereco obterEndereco(@PathVariable long id) {
        List<Endereco> endereco = repositorio.findAll();
        return selecionador.selecionador(endereco, id);     
    }

    @GetMapping("/enderecos")
    public List<Endereco> obterEnderecos() {
        List<Endereco> enderecos = repositorio.findAll();
        return enderecos;
    }

    @PostMapping("/cadastrar")
    public void cadastrarEndereco(@RequestBody Endereco endereco) {
        repositorio.save(endereco);
    }

    @PutMapping("/atualizar")
    public void atualizarEndereco(@RequestBody Endereco atualizacao) {
        Endereco endereco = repositorio.getById(atualizacao.getId());
        EnderecoAtualizador atualizador = new EnderecoAtualizador();
        atualizador.atualizar(endereco, atualizacao);
        repositorio.save(endereco);
    }
    
    @DeleteMapping("/excluir")
    public void excluirEndereco(@RequestBody Endereco excluir) {
        Endereco endereco = repositorio.getById(excluir.getId());
        repositorio.delete(endereco);
    }
    
}
