package com.autobots.automanager.modelos;

import java.util.List;
import org.springframework.stereotype.Component;
import com.autobots.automanager.entitades.CredencialCodigoBarra;

@Component
public class CredencialCodigoBarraSelecionador {

    public CredencialCodigoBarra selecionar(List<CredencialCodigoBarra> credenciais, long id) {
        CredencialCodigoBarra selecionada = null;
        for (CredencialCodigoBarra credencial : credenciais) {
            if (credencial.getId() == id) {
                selecionada = credencial;
                break;
            }
        }
        return selecionada;
    }
}