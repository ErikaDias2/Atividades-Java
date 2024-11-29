package com.autobots.automanager.modelos;

import com.autobots.automanager.entitades.Endereco;

public class EnderecoAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    private void atualizarDados(Endereco endereco, Endereco atualizacao) {
        if (!verificador.verificar(atualizacao.getEstado())) {
            endereco.setEstado(atualizacao.getEstado());
        }
        if (!verificador.verificar(atualizacao.getCidade())) {
            endereco.setCidade(atualizacao.getCidade());
        }
        if (!verificador.verificar(atualizacao.getBairro())) {
            endereco.setBairro(atualizacao.getBairro());
        }
        if (!verificador.verificar(atualizacao.getRua())) {
            endereco.setRua(atualizacao.getRua());
        }
        if (!verificador.verificar(atualizacao.getNumero())) {
            endereco.setNumero(atualizacao.getNumero());
        }
        if (!verificador.verificar(atualizacao.getCodigoPostal())) {
            endereco.setCodigoPostal(atualizacao.getCodigoPostal());
        }
        if (!verificador.verificar(atualizacao.getInformacoesAdicionais())) {
            endereco.setInformacoesAdicionais(atualizacao.getInformacoesAdicionais());
        }
    }

    public void atualizar(Endereco endereco, Endereco atualizacao) {
        atualizarDados(endereco, atualizacao);
    }
}