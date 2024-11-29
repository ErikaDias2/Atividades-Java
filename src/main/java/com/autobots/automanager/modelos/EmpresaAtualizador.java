package com.autobots.automanager.modelos;

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Telefone;

public class EmpresaAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();
    private EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();
    private TelefoneAtualizador telefoneAtualizador = new TelefoneAtualizador();

    private void atualizarDados(Empresa empresa, Empresa atualizacao) {
        if (!verificador.verificar(atualizacao.getRazaoSocial())) {
            empresa.setRazaoSocial(atualizacao.getRazaoSocial());
        }
        if (!verificador.verificar(atualizacao.getNomeFantasia())) {
            empresa.setNomeFantasia(atualizacao.getNomeFantasia());
        }
        if (atualizacao.getEndereco() != null) {
            enderecoAtualizador.atualizar(empresa.getEndereco(), atualizacao.getEndereco());
        }

        // Atualizando telefones existentes
        for (Telefone telefoneAtual : empresa.getTelefones()) {
            for (Telefone telefoneNovo : atualizacao.getTelefones()) {
                if (telefoneAtual.getId().equals(telefoneNovo.getId())) {
                    telefoneAtualizador.atualizar(telefoneAtual, telefoneNovo);
                }
            }
        }

        // Adicionando novos telefones, se houver
        for (Telefone telefoneNovo : atualizacao.getTelefones()) {
            if (telefoneNovo.getId() == null || empresa.getTelefones().stream().noneMatch(t -> t.getId().equals(telefoneNovo.getId()))) {
                empresa.getTelefones().add(telefoneNovo);
            }
        }
    }

    public void atualizar(Empresa empresa, Empresa atualizacao) {
        if (empresa != null && atualizacao != null) {
            atualizarDados(empresa, atualizacao);
        }
    }
}