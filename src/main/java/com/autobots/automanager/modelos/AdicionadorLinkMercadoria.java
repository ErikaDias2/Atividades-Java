package com.autobots.automanager.modelos;

import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.controles.MercadoriaController;

@Component
public class AdicionadorLinkMercadoria implements AdicionadorLink<Mercadoria> {

    @Override
    public void adicionarLink(List<Mercadoria> lista) {
        for (Mercadoria mercadoria : lista) {
            long id = mercadoria.getId();
            Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MercadoriaController.class).obterMercadoria(id)).withSelfRel();
            mercadoria.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Mercadoria objeto) {
        Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MercadoriaController.class).obterMercadorias()).withRel("mercadorias");
        objeto.add(linkProprio);
    }
}