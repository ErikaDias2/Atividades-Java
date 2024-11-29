package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.autobots.automanager.entitades.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}