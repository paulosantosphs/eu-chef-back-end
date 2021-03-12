package br.com.euchef.webservice.models;

import javax.persistence.*;


@Table(name = "pratos")
public class Prato {

    @Id
    @Column(name = "id_prato")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_prato;

    @Column(name = "nivel_prato")
    private long nivel;

    @Column(name = "nome_prato")
    private String nome;

    public Prato() {
    }

    public Prato(final long id_prato, final long nivel, String nome) {
        this.id_prato = id_prato;
        this.nivel = nivel;
        this.nome = nome;
    }


    public long getId_prato() {
        return id_prato;
    }

    public long getNivel() {
        return nivel;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public int hashCode() {
        return (int) id_prato;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Prato other = (Prato) obj;
        if (id_prato != other.id_prato)
            return false;
        return true;
    }
}