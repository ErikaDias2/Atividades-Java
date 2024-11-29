package com.autobots.automanager.modelos;

import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import com.autobots.automanager.entitades.Credencial;
import com.autobots.automanager.controles.CredencialController;

@Component
public class AdicionadorLinkCredencial implements AdicionadorLink<Credencial> {

    @Override
    public void adicionarLink(List<Credencial> lista) {
        for (Credencial credencial : lista) {
            long id = credencial.getId();
            Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CredencialController.class).obterCredencial(id)).withSelfRel();
            credencial.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Credencial objeto) {
        Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CredencialController.class).obterCredenciais()).withRel("credenciais");
        objeto.add(linkProprio);
    }
}
