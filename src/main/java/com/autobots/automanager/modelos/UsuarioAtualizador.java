package com.autobots.automanager.modelos;

import com.autobots.automanager.entitades.Telefone;
import com.autobots.automanager.entitades.Usuario;

public class UsuarioAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();
    private EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();
    private TelefoneAtualizador telefoneAtualizador = new TelefoneAtualizador();

    private void atualizarDados(Usuario usuario, Usuario atualizacao) {
        // Atualizar nome e nome social
        if (!verificador.verificar(atualizacao.getNome())) {
            usuario.setNome(atualizacao.getNome());
        }
        if (!verificador.verificar(atualizacao.getNomeSocial())) {
            usuario.setNomeSocial(atualizacao.getNomeSocial());
        }

        // Atualizar endereço
        if (atualizacao.getEndereco() != null) {
            enderecoAtualizador.atualizar(usuario.getEndereco(), atualizacao.getEndereco());
        }

        // Atualizar telefones existentes
        for (Telefone telefoneAtual : usuario.getTelefones()) {
            for (Telefone telefoneNovo : atualizacao.getTelefones()) {
                if (telefoneAtual.getId().equals(telefoneNovo.getId())) {
                    telefoneAtualizador.atualizar(telefoneAtual, telefoneNovo);
                }
            }
        }

        // Adicionar novos telefones, se houver
        for (Telefone telefoneNovo : atualizacao.getTelefones()) {
            if (telefoneNovo.getId() == null || usuario.getTelefones().stream().noneMatch(t -> t.getId().equals(telefoneNovo.getId()))) {
                usuario.getTelefones().add(telefoneNovo);
            }
        }
    }

    public void atualizar(Usuario usuario, Usuario atualizacao) {
        if (usuario != null && atualizacao != null) {
            atualizarDados(usuario, atualizacao);
        }
    }
}