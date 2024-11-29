package com.autobots.automanager.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.entidades.Usuario;

public interface RepositorioVenda extends JpaRepository<Venda, Long> {
    // Método para buscar vendas pelo cliente
    List<Venda> findByCliente(Usuario cliente);
    List<Venda> findByFuncionario(Usuario funcionario);
}