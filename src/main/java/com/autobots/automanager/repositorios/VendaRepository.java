package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.autobots.automanager.entitades.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
}
