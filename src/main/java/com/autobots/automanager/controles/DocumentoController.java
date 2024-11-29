package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.entitades.Documento;
import com.autobots.automanager.modelos.DocumentoAtualizador;
import com.autobots.automanager.modelos.DocumentoSelecionador;
import com.autobots.automanager.modelos.AdicionadorLinkDocumento;
import com.autobots.automanager.repositorios.DocumentoRepository;

@RestController
@RequestMapping("/documentos")
public class DocumentoController {

    @Autowired
    private DocumentoRepository repository;

    @Autowired
    private DocumentoSelecionador selecionador;

    @Autowired
    private AdicionadorLinkDocumento adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Documento> obterDocumento(@PathVariable long id) {
        List<Documento> documentos = repository.findAll();
        Documento documento = selecionador.selecionar(documentos, id);
        if (documento == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(documento);
            return new ResponseEntity<>(documento, HttpStatus.FOUND);
        }
    }

    @GetMapping("/documentos")
    public ResponseEntity<List<Documento>> obterDocumentos() {
        List<Documento> documentos = repository.findAll();
        if (documentos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(documentos);
            return new ResponseEntity<>(documentos, HttpStatus.FOUND);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> criarDocumento(@RequestBody Documento documento) {
        if (documento.getId() == null) {
            repository.save(documento);
            adicionadorLink.adicionarLink(documento);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarDocumento(@RequestBody Documento atualizacao) {
        Documento documento = repository.getById(atualizacao.getId());
        if (documento != null) {
            DocumentoAtualizador atualizador = new DocumentoAtualizador();
            atualizador.atualizar(documento, atualizacao);
            repository.save(documento);
            adicionadorLink.adicionarLink(documento);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirDocumento(@RequestBody Documento exclusao) {
        Documento documento = repository.getById(exclusao.getId());
        if (documento != null) {
            repository.delete(documento);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}