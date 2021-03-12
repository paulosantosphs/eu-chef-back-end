package br.com.euchef.webservice.tests;

import br.com.euchef.webservice.lucene.IndexSearcherService;
import br.com.euchef.webservice.lucene.ReceitaLuceneRepository;
import br.com.euchef.webservice.lucene.ReceitaService;
import br.com.euchef.webservice.models.Prato;
import br.com.euchef.webservice.models.Receita;
import br.com.euchef.webservice.objects.Frequencia;
import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import static org.mockito.Mockito.*;

public class WikidishesApplicationTests {
    @InjectMocks
    private ReceitaService busca;

    @Mock
    private ReceitaLuceneRepository searcherService;

    @Mock
    private IndexSearcherService index;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testAutoCompletePratos() throws Exception {
        List<Receita> receitas = new ArrayList<>();
        Receita receita = new Receita("4457", "bolo de chocolate", "1", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
        receitas.add(receita);
        Receita receita1 = new Receita("4458", "bolo vermelho", "1", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
        receitas.add(receita1);
        Prato prato = new Prato(4457, 1, "bolo de chocolate");
        LinkedHashSet<Prato> pratos = new LinkedHashSet<>();
        pratos.add(prato);

        when(searcherService.searchFor("bolo de", "nome_prato", 5000)).thenReturn(receitas);
        Assert.assertEquals(pratos, busca.getPratosByPratoName("bolo de"));
        Mockito.verify(searcherService, Mockito.atLeastOnce()).searchFor("bolo de", "nome_prato", 5000);

    }

    @Test
    public void testAutoCompleteIngredientes() throws Exception {
        List<Receita> receitas = new ArrayList<>();
        Receita receita = new Receita("4457", "estrogonofe", "1", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "creme de leite@frango@farinha de trigo@creme de milho", "", "", "", "", "", "", "", "", "");
        receitas.add(receita);
        Receita receitad = new Receita("4457", "estrogonofe", "1", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "creme de coco@frango@farinha de trigo@creme", "", "", "", "", "", "", "", "", "");
        receitas.add(receitad);

        LinkedHashSet<String> ingredientes = new LinkedHashSet<>();
        ingredientes.add("creme de leite");
        ingredientes.add("creme de milho");
        ingredientes.add("creme de coco");
        ingredientes.add("creme");

        when(searcherService.searchFor("creme", "nome_ingrediente", 5000)).thenReturn(receitas);
        Assert.assertEquals(ingredientes, busca.getIngredientes("creme", "nome_ingrediente"));
        verify(searcherService, atLeastOnce()).searchFor("creme", "nome_ingrediente", 5000);

    }

    @Test
    public void testeFrequenciaIngrediente() throws IOException, ParseException {
        List<Receita> receitas = new ArrayList<>();
        Receita receita1 = new Receita("4457", "estrogonofe", "1", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "creme de leite@frango@farinha de trigo@sal", "", "", "", "", "", "", "", "", "");
        receitas.add(receita1);
        Receita receita2 = new Receita("4457", "estrogonofe", "1", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "creme de leite@frango@farinha de trigo@sal", "", "", "", "", "", "", "", "", "");
        receitas.add(receita2);
        Receita receita3 = new Receita("4457", "estrogonofe", "1", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "creme de leite@soja@farinha de trigo@creme de milho", "", "", "", "", "", "", "", "", "");
        receitas.add(receita3);
        Receita receita4 = new Receita("4457", "estrogonofe", "1", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "creme de leite@soja@farinha de trigo@milho", "", "", "", "", "", "", "", "", "");
        receitas.add(receita4);

        LinkedHashSet<String> setIngredientes = new LinkedHashSet<String>();
        setIngredientes.add("creme de leite");
        setIngredientes.add("frango");
        setIngredientes.add("farinha de trigo");
        setIngredientes.add("sal");
        setIngredientes.add("soja");
        setIngredientes.add("creme de milho");
        setIngredientes.add("milho");

        List<Frequencia> frequencia = new ArrayList<>();
        frequencia.add(new Frequencia("creme de leite", 100.0));
        frequencia.add(new Frequencia("frango", 50.0));
        frequencia.add(new Frequencia("farinha de trigo", 100.0));
        frequencia.add(new Frequencia("sal", 50.0));
        frequencia.add(new Frequencia("soja", 50.0));
        frequencia.add(new Frequencia("creme de milho", 25.0));
        frequencia.add(new Frequencia("milho", 25.0));
        Collections.sort(frequencia, (o2, o1) -> Double.compare(o1.getFrequencia(), o2.getFrequencia()));
        when(index.getCount("estrogonofe", "nome_pratoB")).thenReturn(4);
        when(searcherService.searchFor("estrogonofe", "nome_pratoB", 4)).thenReturn(receitas);

        when(index.getCountForTwoFields("estrogonofe", "cremedeleite", "nome_pratoB", "nome_ingredienteB")).thenReturn(4);
        when(index.getCountForTwoFields("estrogonofe", "frango", "nome_pratoB", "nome_ingredienteB")).thenReturn(2);
        when(index.getCountForTwoFields("estrogonofe", "farinhadetrigo", "nome_pratoB", "nome_ingredienteB")).thenReturn(4);
        when(index.getCountForTwoFields("estrogonofe", "sal", "nome_pratoB", "nome_ingredienteB")).thenReturn(2);
        when(index.getCountForTwoFields("estrogonofe", "soja", "nome_pratoB", "nome_ingredienteB")).thenReturn(2);
        when(index.getCountForTwoFields("estrogonofe", "cremedemilho", "nome_pratoB", "nome_ingredienteB")).thenReturn(1);
        when(index.getCountForTwoFields("estrogonofe", "milho", "nome_pratoB", "nome_ingredienteB")).thenReturn(1);


        Assert.assertEquals(setIngredientes, busca.getIngredientesDoPrato("estrogonofe"));
        Assert.assertEquals(busca.getFrequenciaIngrediente("estrogonofe"), frequencia);


    }
}
