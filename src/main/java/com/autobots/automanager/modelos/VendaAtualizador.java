package com.autobots.automanager.modelos;

import com.autobots.automanager.entitades.Venda;

public class VendaAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    private void atualizarDados(Venda venda, Venda atualizacao) {
        if (!verificador.verificar(atualizacao.getIdentificacao())) {
            venda.setIdentificacao(atualizacao.getIdentificacao());
        }
        if (atualizacao.getCliente() != null) {
            venda.setCliente(atualizacao.getCliente());
        }
        if (atualizacao.getFuncionario() != null) {
            venda.setFuncionario(atualizacao.getFuncionario());
        }
        if (!atualizacao.getMercadorias().isEmpty()) {
            venda.setMercadorias(atualizacao.getMercadorias());
        }
        if (!atualizacao.getServicos().isEmpty()) {
            venda.setServicos(atualizacao.getServicos());
        }
    }

    public void atualizar(Venda venda, Venda atualizacao) {
        atualizarDados(venda, atualizacao);
    }
}