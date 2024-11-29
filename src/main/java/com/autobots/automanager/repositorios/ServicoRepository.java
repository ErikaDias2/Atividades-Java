package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.autobots.automanager.entitades.Servico;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
}
