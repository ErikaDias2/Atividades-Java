package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.autobots.automanager.entitades.CredencialCodigoBarra;

@Repository
public interface CredencialCodigoBarraRepository extends JpaRepository<CredencialCodigoBarra, Long> {
}
