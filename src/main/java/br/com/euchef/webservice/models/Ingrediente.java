package br.com.euchef.webservice.models;

import javax.persistence.*;


@Table(name = "ingredientes")
public class Ingrediente {
    @Id
    @Column(name = "id_ingrediente")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "ing_principal")
    private long ingPrincipal;

    public Ingrediente() {
    }

    public Ingrediente(final long id, String nome, final long ingPrincipal) {
        this.id = id;
        this.nome = nome;
        this.ingPrincipal = ingPrincipal;
    }

    public String getNome() {
        return nome;
    }

    public long getIngPrincipal() {
        return ingPrincipal;
    }

}