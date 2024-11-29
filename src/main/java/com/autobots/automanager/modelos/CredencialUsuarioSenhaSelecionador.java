package com.autobots.automanager.modelos;

import java.util.List;
import org.springframework.stereotype.Component;
import com.autobots.automanager.entitades.CredencialUsuarioSenha;

@Component
public class CredencialUsuarioSenhaSelecionador {

    public CredencialUsuarioSenha selecionar(List<CredencialUsuarioSenha> credenciais, long id) {
        CredencialUsuarioSenha selecionada = null;
        for (CredencialUsuarioSenha credencial : credenciais) {
            if (credencial.getId() == id) {
                selecionada = credencial;
                break;
            }
        }
        return selecionada;
    }
}