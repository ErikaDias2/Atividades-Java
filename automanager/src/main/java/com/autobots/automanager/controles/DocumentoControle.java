package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.DocumentoAtualizador;
import com.autobots.automanager.modelo.DocumentoSelecionador;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/documento")
public class DocumentoControle {
    @Autowired
    private DocumentoRepositorio repositorio;
    @Autowired
    private DocumentoSelecionador selecionador;

    @GetMapping("/{id}")
    public Documento obterDocumento(@PathVariable long id) {
        List<Documento> documentos = repositorio.findAll();
        return selecionador.selecionador(documentos, id);
    }

    @GetMapping("/documentos")
    public List<Documento> obterDocumentos() {
        List<Documento> documentos = repositorio.findAll();
        return documentos;
    }

    @PostMapping("/cadastrar")
    public void cadastrarDocumento(@RequestBody Documento documento) {
        repositorio.save(documento);
    }
    
    @PutMapping("/atualizar")
    public void atualizarDocumento(@RequestBody Documento atualizacao) {
        Documento documento = repositorio.getById(atualizacao.getId());
        DocumentoAtualizador atualizador = new DocumentoAtualizador();
        atualizador.atualizar(documento, atualizacao);
        repositorio.save(documento);
    }

    @DeleteMapping("/excluir")
    public void excluirDocumento(@RequestBody Documento excluir) {
        Documento documento = repositorio.getById(excluir.getId());
        repositorio.delete(documento);
    }
    
}
