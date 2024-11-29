package com.autobots.automanager.modelos;

import java.util.List;
import org.springframework.stereotype.Component;
import com.autobots.automanager.entitades.Empresa;

@Component
public class EmpresaSelecionador {
    public Empresa selecionar(List<Empresa> empresas, long id) {
        Empresa selecionada = null;
        for (Empresa empresa : empresas) {
            if (empresa.getId() == id) {
                selecionada = empresa;
            }
        }
        return selecionada;
    }
}