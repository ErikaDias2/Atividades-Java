package com.autobots.automanager.entidades;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private double valor;

    @Column
    private String descricao;
}