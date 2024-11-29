package com.autobots.automanager.modelos;

import com.autobots.automanager.entitades.Mercadoria;

public class MercadoriaAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    private void atualizarDados(Mercadoria mercadoria, Mercadoria atualizacao) {
        if (!verificador.verificar(atualizacao.getNome())) {
            mercadoria.setNome(atualizacao.getNome());
        }
        if (atualizacao.getQuantidade() != 0) {
            mercadoria.setQuantidade(atualizacao.getQuantidade());
        }
        if (atualizacao.getValor() != 0) {
            mercadoria.setValor(atualizacao.getValor());
        }
        if (!verificador.verificar(atualizacao.getDescricao())) {
            mercadoria.setDescricao(atualizacao.getDescricao());
        }
    }

    public void atualizar(Mercadoria mercadoria, Mercadoria atualizacao) {
        atualizarDados(mercadoria, atualizacao);
    }
}