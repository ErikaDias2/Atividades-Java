package com.autobots.automanager.modelos;

import com.autobots.automanager.entitades.Credencial;

public class CredencialAtualizador {

    private void atualizarDados(Credencial credencial, Credencial atualizacao) {
        if (!(atualizacao.getCriacao() == null)) {
            credencial.setCriacao(atualizacao.getCriacao());
        }
        if (!(atualizacao.getUltimoAcesso() == null)) {
            credencial.setUltimoAcesso(atualizacao.getUltimoAcesso());
        }
        credencial.setInativo(atualizacao.isInativo());
    }

    public void atualizar(Credencial credencial, Credencial atualizacao) {
        if (credencial != null && atualizacao != null) {
            atualizarDados(credencial, atualizacao);
        }
    }
}