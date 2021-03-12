package br.com.euchef.webservice.lucene;

import br.com.euchef.webservice.models.Prato;
import br.com.euchef.webservice.models.Receita;
import br.com.euchef.webservice.objects.Filtro;
import br.com.euchef.webservice.objects.Frequencia;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class ReceitaService {
    private final static String CARACTERE_NIVEL_1 = "@";

    @Autowired
    private ReceitaLuceneRepository service;

    @Autowired
    private IndexSearcherService serviceIndex;

    public ReceitaService() {
    }

    //Torna ingredientes como "farinha de trigo" em "farinhadetrigo"
    public static String padronizaNome(String ingb) {
        String inga = ingb.replace(" ", "");
        inga = inga.toLowerCase();
        return inga;
    }

    public LinkedHashSet<Prato> getPratosByIngredienteName(String nomeIngrediente) throws ParseException, IOException {
        //int max = serviceIndex.getCountForPratos(padronizaNome(nomeIngrediente), "nome_ingredienteB");
        List<Receita> receitas = service.searchForPratos(padronizaNome(nomeIngrediente), "nome_ingredienteB", 10000);
        List<Prato> pratos = new ArrayList<Prato>();
        for (Receita receita : receitas) {
            pratos.add(convert(receita));
        }
        LinkedHashSet<Prato> setPratos = new LinkedHashSet<Prato>(pratos);
        return setPratos;
    }

    public LinkedHashSet<Prato> getPratosByPratoName(String nomePrato) throws ParseException, IOException {
        //int max = serviceIndex.getCount(nomePrato, "nome_prato");
        nomePrato = nomePrato.toLowerCase();
        List<Receita> receitas = service.searchFor(nomePrato, "nome_prato", 5000);
        List<Prato> pratos = new ArrayList<Prato>();
        for (Receita receita : receitas) {
            if (receita.getNome_prato().contains(nomePrato)) {
                pratos.add(convert(receita));
            }
        }
        LinkedHashSet<Prato> SetPratos = new LinkedHashSet<Prato>(pratos);
        return SetPratos;

    }


    public LinkedHashSet<String> getIngredientes(String nomeIngrediente, String field) throws ParseException, IOException {
        //int max = serviceIndex.getCount(nomeIngrediente, field);
        nomeIngrediente = nomeIngrediente.toLowerCase();
        List<Receita> receitas = service.searchFor(nomeIngrediente, field, 5000);
        List<String> ingredientes = new ArrayList<String>();
        for (Receita receita : receitas) {
            String ings[] = (receita.getNome_ingrediente()).split(CARACTERE_NIVEL_1);
            String termIngs[] = nomeIngrediente.split(" ");
            for (int i = 0; i < ings.length; i++) {
                int flag = 0;
                for (int j = 0; j < termIngs.length; j++) {
                    if (ings[i].contains(termIngs[j])) {
                        flag++;
                    }
                }
                if (flag == termIngs.length) {
                    ingredientes.add(ings[i]);
                }
            }
        }

        LinkedHashSet<String> SetIngredientes = new LinkedHashSet<String>(ingredientes);
        return SetIngredientes;

    }

    public List<Receita> getReceitaByPrato(String nomePrato, String idPrato, String field) throws ParseException, IOException {
        //int max = serviceIndex.getCount(padronizaNome(nomePrato), "nome_pratoB");
        List<Receita> receitas = service.searchFor(padronizaNome(nomePrato), "nome_pratoB", 10000);
        List<Receita> rec = new ArrayList<Receita>();
        for (Receita receita : receitas) {
            if (receita.getId_prato().equals(idPrato) && receita.getNome_prato().equals(nomePrato)) {
                rec.add(receita);
            } else
                break;
        }
        return rec;

    }


    public List<Receita> getReceitaByPratoAndIng(String nomePrato, String idPrato, String ingrediente, String fieldPrato, String fieldIng) throws ParseException, IOException {
        //int max = serviceIndex.getCountForTwoFields(padronizaNome(nomePrato), padronizaNome(ingrediente), fieldPrato, fieldIng);
        List<Receita> receitas = service.searchForTwoFields(padronizaNome(nomePrato), padronizaNome(ingrediente), fieldPrato, fieldIng, 20000);
        List<Receita> rec = new ArrayList<Receita>();
        for (Receita receita : receitas) {
            if (receita.getId_prato().equals(idPrato) && receita.getNome_prato().equals(nomePrato)) {
                rec.add(receita);
            } else
                break;
        }
        return rec;

    }

    public LinkedHashSet<String> getIngredientesByPrato(String nomePrato, String idPrato, String ingrediente, String fieldPrato, String fieldIng) throws ParseException, IOException {
        //int max = serviceIndex.getCountForTwoFields(padronizaNome(nomePrato), ingrediente, fieldPrato, fieldIng);
        ingrediente = ingrediente.toLowerCase();
        List<Receita> receitas = service.searchForTwoFields(padronizaNome(nomePrato), ingrediente, fieldPrato, fieldIng, 20000);
        List<String> ingredientes = new ArrayList<String>();
        for (Receita receita : receitas) {
            if (receita.getId_prato().equals(idPrato)) {

                String ings[] = (receita.getNome_ingrediente()).split(CARACTERE_NIVEL_1);
                String termIngs[] = ingrediente.split(" ");
                for (int i = 0; i < ings.length; i++) {
                    int flag = 0;
                    for (int j = 0; j < termIngs.length; j++) {
                        if (ings[i].contains(termIngs[j])) {
                            flag++;
                        }
                    }
                    if (flag == termIngs.length) {
                        ingredientes.add(ings[i]);
                    }
                }
            }
        }
        LinkedHashSet<String> SetIngredientes = new LinkedHashSet<String>(ingredientes);
        return SetIngredientes;

    }

    public List<Frequencia> getFrequenciaIngrediente(String nomePrato) throws IOException, ParseException {
        LinkedHashSet<String> ingredientes = getIngredientesDoPrato(nomePrato);
        List<Frequencia> frequencias = new ArrayList<>();
        int total = serviceIndex.getCount(padronizaNome(nomePrato), "nome_pratoB");
        for (String ingrediente : ingredientes) {
            int c = serviceIndex.getCountForTwoFields(padronizaNome(nomePrato), padronizaNome(ingrediente), "nome_pratoB", "nome_ingredienteB");
            double porcentagem = calculoPorcentagem(total, c);
            Frequencia freq = new Frequencia(ingrediente, porcentagem);
            frequencias.add(freq);
        }
        Collections.sort(frequencias, (o2, o1) -> Double.compare(o1.getFrequencia(), o2.getFrequencia()));
        return frequencias;
    }


    public LinkedHashSet<String> getIngredientesDoPrato(String nomePrato) throws IOException, ParseException {
        int c = serviceIndex.getCount(padronizaNome(nomePrato), "nome_pratoB");
        List<Receita> receitas = service.searchFor(padronizaNome(nomePrato), "nome_pratoB", c);
        List<String> ingredientes = new ArrayList<>();
        for (Receita receita : receitas) {
            String ings[] = (receita.getNome_ingrediente()).split(CARACTERE_NIVEL_1);
            for (int i = 0; i < ings.length; i++) {
                if (!ings[i].isEmpty()) {
                    ingredientes.add(ings[i]);
                }
            }
        }
        LinkedHashSet<String> setIngredientes = new LinkedHashSet<String>(ingredientes);
        return setIngredientes;
    }

    public Filtro getFiltro(String nomePrato, String ingredientesDesejados, String ingredientesNaoDesejados) throws IOException, ParseException {
        int totalPrato = serviceIndex.getCount(padronizaNome(nomePrato), "nome_pratoB");
        int totalFiltro = serviceIndex.getCountForFilter(padronizaNome(nomePrato), padronizaNome(ingredientesDesejados), padronizaNome(ingredientesNaoDesejados), "nome_pratoB", "nome_ingredienteB");
        return new Filtro(totalPrato, totalFiltro);
    }

    public List<Receita> getReceitaByFiltro(String nomePrato, String ingredientesDesejados, String ingredientesNaoDesejados) throws IOException, ParseException {
        int totalFiltro = serviceIndex.getCountForFilter(padronizaNome(nomePrato), padronizaNome(ingredientesDesejados), padronizaNome(ingredientesNaoDesejados), "nome_pratoB", "nome_ingredienteB");
        List<Receita> receitas = service.searchForFilter(padronizaNome(nomePrato), padronizaNome(ingredientesDesejados), padronizaNome(ingredientesNaoDesejados), "nome_pratoB", "nome_ingredienteB", totalFiltro);
        return receitas;
    }

    public List<Frequencia> getCategoriasByIng(String ingredientes) throws IOException, ParseException {
        int max = serviceIndex.getCountForPratos(padronizaNome(ingredientes), "nome_ingredienteB");
        List<Receita> receitas = service.searchForPratos(padronizaNome(ingredientes), "nome_ingredienteB", 30000);
        List<String> categorias = new ArrayList<String>();
        List<Prato> pratos1 = new ArrayList<Prato>();
        List<Frequencia> catFreqs = new ArrayList<>();
        for (Receita receita : receitas) {
            categorias.add(receita.getCat1().replace("-", " "));
            categorias.add(receita.getCat2().replace("-", " "));
            categorias.add(receita.getCat3().replace("-", " "));
            // tratar no indexador os caracteres: / \ - "
            pratos1.add(convert(receita));
        }
        LinkedHashSet<String> setCategorias = new LinkedHashSet<String>(categorias);
        LinkedHashSet<Prato> setPratos1 = new LinkedHashSet<Prato>(pratos1);
        for (String cat : setCategorias) {
            if (!cat.equals("")) {
                //int c = serviceIndex.getCountForCat(padronizaNome(ingredientes), "nome_ingredienteB", padronizaNome(cat), "catB");
                List<Receita> recs = service.searchForCat(padronizaNome(ingredientes), "nome_ingredienteB", padronizaNome(cat), "catB", 20000);
                List<Prato> pratos = new ArrayList<Prato>();
                for (Receita rec : recs) {
                    pratos.add(convert(rec));
                }
                LinkedHashSet<Prato> setPratos = new LinkedHashSet<Prato>(pratos);
                Frequencia freq = new Frequencia(cat, calculoPorcentagem(setPratos1.size(), setPratos.size()));
                catFreqs.add(freq);
            }

        }
        Collections.sort(catFreqs, (o2, o1) -> Double.compare(o1.getFrequencia(), o2.getFrequencia()));
        return catFreqs;

    }

    public LinkedHashSet<Prato> getReceitaByCatAndIngs(String ingredientes, String categorias) throws IOException, ParseException {
        //int max = serviceIndex.getCountForCat(padronizaNome(ingredientes), "nome_ingredienteB", padronizaNome(categorias), "catB");
        List<Receita> receitas = service.searchForCat(padronizaNome(ingredientes), "nome_ingredienteB", padronizaNome(categorias), "catB", 10000);
        List<Prato> pratos = new ArrayList<Prato>();
        for (Receita receita : receitas) {
            pratos.add(convert(receita));
        }
        LinkedHashSet<Prato> setPratos = new LinkedHashSet<Prato>(pratos);
        return setPratos;
    }


    private Prato convert(Receita receita) {
        return new Prato(Integer.parseInt(receita.getId_prato()), Integer.parseInt(receita.getNivel_prato()), receita.getNome_prato());
    }

    public double calculoPorcentagem(int total, int contagem) {
        double p = 100 * (double) contagem / total;
        return (Math.round(p * 100.0) / 100.0);
    }


}