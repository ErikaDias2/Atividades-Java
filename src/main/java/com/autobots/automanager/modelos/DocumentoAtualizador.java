package com.autobots.automanager.modelos;

import com.autobots.automanager.entitades.Documento;

public class DocumentoAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    private void atualizarDados(Documento documento, Documento atualizacao) {
        if (!verificador.verificar(atualizacao.getNumero())) {
            documento.setNumero(atualizacao.getNumero());
        }
        if (!(atualizacao.getDataEmissao() == null)) {
            documento.setDataEmissao(atualizacao.getDataEmissao());
        }
        if (atualizacao.getTipo() != null) {
            documento.setTipo(atualizacao.getTipo());
        }
    }

    public void atualizar(Documento documento, Documento atualizacao) {
        atualizarDados(documento, atualizacao);
    }
}