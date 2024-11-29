package com.autobots.automanager.modelos;

import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import com.autobots.automanager.entitades.Email;
import com.autobots.automanager.controles.EmailController;

@Component
public class AdicionadorLinkEmail implements AdicionadorLink<Email> {

    @Override
    public void adicionarLink(List<Email> lista) {
        for (Email email : lista) {
            long id = email.getId();
            Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmailController.class).obterEmail(id)).withSelfRel();
            email.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Email objeto) {
        Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmailController.class).obterEmails()).withRel("emails");
        objeto.add(linkProprio);
    }
}
