package br.com.euchef.webservice.objects;

/**
 * Created by paulo on 22/11/16.
 */
public class Filtro {
    private long totalPrato;
    private long totalFiltro;

    public long getTotalPrato() {
        return totalPrato;
    }

    public void setTotalPrato(long totalPrato) {
        this.totalPrato = totalPrato;
    }

    public long getTotalFiltro() {
        return totalFiltro;
    }

    public void setTotalFiltro(long totalFiltro) {
        this.totalFiltro = totalFiltro;
    }

    public Filtro(long totalPrato, long totalFiltro) {
        this.totalPrato = totalPrato;
        this.totalFiltro = totalFiltro;
    }
}
