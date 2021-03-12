package br.com.euchef.webservice.controllers;

import br.com.euchef.webservice.lucene.ReceitaService;
import br.com.euchef.webservice.models.Prato;
import br.com.euchef.webservice.models.Receita;
import br.com.euchef.webservice.objects.Filtro;
import br.com.euchef.webservice.objects.Frequencia;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;

@RestController
@RequestMapping("/")
public class Controller {

    @Autowired
    private ReceitaService searcher;

    /*@CrossOrigin(origins = "*")
    @RequestMapping("/pratos")
    public List<Prato> getTodosPratos() {
        return pratoRepository.findAll();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/ingredientes")
    public List<Ingrediente> getIngredientes() {
        return ingredienteRepository.findAll();
    }
    */

    /**
     * Buca prato por ingredientes
     * Separar diferentes ingredientes por "@"
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/pratos/busca/ingredientes", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public LinkedHashSet<Prato> getPratosIngrediente(@RequestParam String ingredientes) throws ParseException, IOException {
        return searcher.getPratosByIngredienteName(ingredientes);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/autocomplete/prato", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public LinkedHashSet<Prato> getPratosNomePrato(@RequestParam String nomePrato) throws ParseException, IOException {
        return searcher.getPratosByPratoName(nomePrato);
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/autocomplete/prato/ingrediente", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public LinkedHashSet<String> getIngredientesByPrato(@RequestParam String nomePrato, String idPrato, String ingrediente) throws ParseException, IOException {

        return searcher.getIngredientesByPrato(nomePrato, idPrato, ingrediente, "nome_pratoB", "nome_ingrediente");
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/autocomplete/ingrediente", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public LinkedHashSet<String> getIngredientes(@RequestParam String nomeIngrediente) throws ParseException, IOException {

        return searcher.getIngredientes(nomeIngrediente, "nome_ingrediente");
    }

    /**
     * Busca receitas por prato
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/receitas/busca/prato", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public List<Receita> getReceitabyPrato(@RequestParam String nomePrato, String idPrato) throws ParseException, IOException {

        return searcher.getReceitaByPrato(nomePrato, idPrato, "nome_pratoB");
    }

    /**
     * Busca receitas por prato e por ingrediente
     * Separar diferentes ingredientes por "@"
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/receitas/busca/prato/ingredientes", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public List<Receita> getReceitabyPratoAndIng(@RequestParam String nomePrato, String idPrato, String ingredientes) throws ParseException, IOException {

        return searcher.getReceitaByPratoAndIng(nomePrato, idPrato, ingredientes, "nome_pratoB", "nome_ingredienteB");
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/frequencia/ingrediente", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public List<Frequencia> getFrequenciaAndIngrediente(@RequestParam String nomePrato) throws ParseException, IOException {

        return searcher.getFrequenciaIngrediente(nomePrato);
    }

    /**
     * Filtro de ingredientes retorna total de receitas do prato e total de receitas do filtro
     * Separar diferentes ingredientes por "@"
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/filtro", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public Filtro getFiltro(@RequestParam String nomePrato, String ingredientesDesejados, String ingredientesNaoDesejados) throws ParseException, IOException {

        return searcher.getFiltro(nomePrato, ingredientesDesejados, ingredientesNaoDesejados);
    }

    /**
     * Filtro de ingredientes que retorna receitas filtradas
     * Separar diferentes ingredientes por "@"
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/receita/filtro", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public List<Receita> getReceitaFiltro(@RequestParam String nomePrato, String ingredientesDesejados, String ingredientesNaoDesejados) throws ParseException, IOException {

        return searcher.getReceitaByFiltro(nomePrato, ingredientesDesejados, ingredientesNaoDesejados);
    }

    /**
     * Categorias presentes em uma busca de pratos por ingredientes
     * Separar diferentes ingredientes por "@"
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/pratos/categorias", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public List<Frequencia> getCategorias(@RequestParam String ingredientes) throws ParseException, IOException {

        return searcher.getCategoriasByIng(ingredientes);
    }


    /**
     * Busca de pratos por categorias
     * Separar diferentes ingredientes e categorias por "@"
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/pratos/busca/categorias/ingredientes", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public LinkedHashSet<Prato> getPratoByCategoriasAndIngs(@RequestParam String ingredientes, String categorias) throws ParseException, IOException {
        return searcher.getReceitaByCatAndIngs(ingredientes, categorias);
    }

}