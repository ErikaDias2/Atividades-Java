package com.autobots.automanager.modelos;

import java.util.List;
import org.springframework.stereotype.Component;
import com.autobots.automanager.entitades.Venda;

@Component
public class VendaSelecionador {
    public Venda selecionar(List<Venda> vendas, long id) {
        Venda selecionada = null;
        for (Venda venda : vendas) {
            if (venda.getId() == id) {
                selecionada = venda;
            }
        }
        return selecionada;
    }
}