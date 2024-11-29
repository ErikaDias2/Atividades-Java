package com.autobots.automanager.modelos;

import com.autobots.automanager.entitades.CredencialUsuarioSenha;

public class CredencialUsuarioSenhaAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    private void atualizarDados(CredencialUsuarioSenha credencial, CredencialUsuarioSenha atualizacao) {
        if (!verificador.verificar(atualizacao.getNomeUsuario())) {
            credencial.setNomeUsuario(atualizacao.getNomeUsuario());
        }
        if (!verificador.verificar(atualizacao.getSenha())) {
            credencial.setSenha(atualizacao.getSenha());
        }
        if (atualizacao.getCriacao() != null) {
            credencial.setCriacao(atualizacao.getCriacao());
        }
        if (atualizacao.getUltimoAcesso() != null) {
            credencial.setUltimoAcesso(atualizacao.getUltimoAcesso());
        }
        credencial.setInativo(atualizacao.isInativo());
    }

    public void atualizar(CredencialUsuarioSenha credencial, CredencialUsuarioSenha atualizacao) {
        if (credencial != null && atualizacao != null) {
            atualizarDados(credencial, atualizacao);
        }
    }
}