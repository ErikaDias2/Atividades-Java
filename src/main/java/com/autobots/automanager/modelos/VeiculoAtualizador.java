package com.autobots.automanager.modelos;

import com.autobots.automanager.entitades.Veiculo;

public class VeiculoAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    private void atualizarDados(Veiculo veiculo, Veiculo atualizacao) {
        if (!verificador.verificar(atualizacao.getModelo())) {
            veiculo.setModelo(atualizacao.getModelo());
        }
        if (!verificador.verificar(atualizacao.getPlaca())) {
            veiculo.setPlaca(atualizacao.getPlaca());
        }
    }

    public void atualizar(Veiculo veiculo, Veiculo atualizacao) {
        atualizarDados(veiculo, atualizacao);
    }
}