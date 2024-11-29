package com.autobots.automanager.modelos;

import java.util.List;
import org.springframework.stereotype.Component;
import com.autobots.automanager.entitades.Mercadoria;

@Component
public class MercadoriaSelecionador {
    public Mercadoria selecionar(List<Mercadoria> mercadorias, long id) {
        Mercadoria selecionada = null;
        for (Mercadoria mercadoria : mercadorias) {
            if (mercadoria.getId() == id) {
                selecionada = mercadoria;
            }
        }
        return selecionada;
    }
}