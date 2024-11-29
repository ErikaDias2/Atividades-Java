package com.autobots.automanager.modelos;

import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.controles.ServicoController;

@Component
public class AdicionadorLinkServico implements AdicionadorLink<Servico> {

    @Override
    public void adicionarLink(List<Servico> lista) {
        for (Servico servico : lista) {
            long id = servico.getId();
            Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ServicoController.class).obterServico(id)).withSelfRel();
            servico.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Servico objeto) {
        Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ServicoController.class).obterServicos()).withRel("servicos");
        objeto.add(linkProprio);
    }
}