package com.autobots.automanager.modelos;

import com.autobots.automanager.entitades.CredencialCodigoBarra;

public class CredencialCodigoBarraAtualizador {

    private void atualizarDados(CredencialCodigoBarra credencial, CredencialCodigoBarra atualizacao) {
        if (atualizacao.getCodigo() != 0) { // Verificando se o código é diferente de 0
            credencial.setCodigo(atualizacao.getCodigo());
        }
        if (atualizacao.getCriacao() != null) {
            credencial.setCriacao(atualizacao.getCriacao());
        }
        if (atualizacao.getUltimoAcesso() != null) {
            credencial.setUltimoAcesso(atualizacao.getUltimoAcesso());
        }
        credencial.setInativo(atualizacao.isInativo());
    }

    public void atualizar(CredencialCodigoBarra credencial, CredencialCodigoBarra atualizacao) {
        if (credencial != null && atualizacao != null) {
            atualizarDados(credencial, atualizacao);
        }
    }
}