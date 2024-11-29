package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.autobots.automanager.entitades.Documento;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
}
