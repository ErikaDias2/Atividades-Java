package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.VendaController;
import com.autobots.automanager.entitades.Venda;

@Component
public class AdicionadorLinkVenda implements AdicionadorLink<Venda> {

    @Override
    public void adicionarLink(List<Venda> lista) {
        for (Venda venda : lista) {
            long id = venda.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(VendaController.class).obterVenda(id))
                    .withSelfRel();
            venda.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Venda objeto) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(VendaController.class).obterVendas())
                .withRel("vendas");
        objeto.add(linkProprio);
    }
}