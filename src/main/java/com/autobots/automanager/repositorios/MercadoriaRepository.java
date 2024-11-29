package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.autobots.automanager.entitades.Mercadoria;

@Repository
public interface MercadoriaRepository extends JpaRepository<Mercadoria, Long> {
}
