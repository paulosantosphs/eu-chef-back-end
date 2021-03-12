package br.com.euchef.webservice.objects;

/**
 * Created by paulo on 21/11/16.
 */
public class Frequencia {
    private String nome;
    private double frequencia;

    public Frequencia(String nome, double frequencia) {
        this.nome = nome;
        this.frequencia = frequencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Frequencia)) return false;

        Frequencia that = (Frequencia) o;

        if (Double.compare(that.frequencia, frequencia) != 0) return false;
        return nome.equals(that.nome);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = nome.hashCode();
        temp = Double.doubleToLongBits(frequencia);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public String getNome() {
        return nome;
    }

    public double getFrequencia() {
        return frequencia;
    }


}
