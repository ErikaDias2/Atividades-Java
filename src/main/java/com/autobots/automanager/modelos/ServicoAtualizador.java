package com.autobots.automanager.modelos;

import com.autobots.automanager.entitades.Servico;

public class ServicoAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    private void atualizarDados(Servico servico, Servico atualizacao) {
        if (!verificador.verificar(atualizacao.getNome())) {
            servico.setNome(atualizacao.getNome());
        }
        if (atualizacao.getValor() != 0) {
            servico.setValor(atualizacao.getValor());
        }
        if (!verificador.verificar(atualizacao.getDescricao())) {
            servico.setDescricao(atualizacao.getDescricao());
        }
    }

    public void atualizar(Servico servico, Servico atualizacao) {
        atualizarDados(servico, atualizacao);
    }
}