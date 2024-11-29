package com.autobots.automanager.modelos;

import java.util.List;
import org.springframework.stereotype.Component;
import com.autobots.automanager.entitades.Credencial;

@Component
public class CredencialSelecionador {
    public Credencial selecionar(List<Credencial> credenciais, long id) {
        Credencial selecionada = null;
        for (Credencial credencial : credenciais) {
            if (credencial.getId() == id) {
                selecionada = credencial;
            }
        }
        return selecionada;
    }
}