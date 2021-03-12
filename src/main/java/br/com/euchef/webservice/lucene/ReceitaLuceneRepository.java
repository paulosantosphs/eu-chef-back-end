package br.com.euchef.webservice.lucene;

import br.com.euchef.webservice.models.Receita;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 18/11/2016.
 */
@Service
public class ReceitaLuceneRepository {

    @Autowired
    private IndexSearcherService searcherService;

    public List<Receita> searchFor(String term, String field, int max)
            throws ParseException, CorruptIndexException, IOException {
        // Cria query de consulta ao indice
        Query query = searcherService.createQuery(term, field);

        // Obtem o top documents encontrado na pesquisa
        ScoreDoc[] scoreDocs = searcherService.retrieveScoreDocsFrom(query, max);

        // Retorna a lista de receitas
        return retrieveResultFrom(scoreDocs);
    }

    /**
     * Realiza as consultas para dois fields
     *
     * @param termA
     * @param termB
     * @param fieldA
     * @param fieldB
     * @param max
     * @return
     * @throws ParseException
     * @throws CorruptIndexException
     * @throws IOException
     */
    public List<Receita> searchForTwoFields(String termA, String termB, String fieldA, String fieldB, int max)
            throws ParseException, CorruptIndexException, IOException {
        // Cria query de consulta ao indice
        Query query = searcherService.createQueryForTwoFields(termA, termB, fieldA, fieldB);

        // Obtem o top documents encontrado na pesquisa
        ScoreDoc[] scoreDocs = searcherService.retrieveScoreDocsFrom(query, max);

        // Retorna a lista de receitas
        return retrieveResultFrom(scoreDocs);
    }

    public List<Receita> searchForPratos(String ingredientes, String field, int max)
            throws ParseException, CorruptIndexException, IOException {
        // Cria query de consulta ao indice
        Query query = searcherService.createQueryPratos(ingredientes, field);

        // Obtem o top documents encontrado na pesquisa
        ScoreDoc[] scoreDocs = searcherService.retrieveScoreDocsFrom(query, max);

        // Retorna a lista de receitas
        return retrieveResultFrom(scoreDocs);
    }

    public List<Receita> searchForFilter(String nomePrato, String ingredientesDesejados, String ingredientesNaoDesejados, String fieldPrato, String fieldIngrediente, int max)
            throws ParseException, CorruptIndexException, IOException {
        // Cria query de consulta ao indice
        Query query = searcherService.createQueryForFilter(nomePrato, ingredientesDesejados, ingredientesNaoDesejados, fieldPrato, fieldIngrediente);

        // Obtem o top documents encontrado na pesquisa
        ScoreDoc[] scoreDocs = searcherService.retrieveScoreDocsFrom(query, max);

        // Retorna a lista de receitas
        return retrieveResultFrom(scoreDocs);
    }

    public List<Receita> searchForCat(String ingredientes, String fieldIng, String categoria, String fieldCat, int max)
            throws ParseException, CorruptIndexException, IOException {
        // Cria query de consulta ao indice
        Query query = searcherService.createQueryCat(ingredientes, fieldIng, categoria, fieldCat);

        // Obtem o top documents encontrado na pesquisa
        ScoreDoc[] scoreDocs = searcherService.retrieveScoreDocsFrom(query, max);

        // Retorna a lista de receitas
        return retrieveResultFrom(scoreDocs);
    }


    /**
     * Converte os documentos retornados em receitas
     *
     * @param scoreDocs
     * @return
     * @throws CorruptIndexException
     * @throws IOException
     */
    public List<Receita> retrieveResultFrom(ScoreDoc[] scoreDocs)
            throws CorruptIndexException, IOException {
        // Trata caso de busca por termo que nao encontra resultados
        List<Receita> receitas = new ArrayList<Receita>();
        if (0 == scoreDocs.length) {
            return receitas;
        }

        // Para cada documento retornando pela consulta,
        // Passa o identificador do documento para entao traze-lo por completo
        List<Document> documents = new ArrayList<Document>();
        for (int i = 0; i < scoreDocs.length; i++) {
            documents.add(this.searcherService.doc(scoreDocs[i].doc));
        }

        // Para cada documento retornando pela consulta,
        // Extrai valor desejado do documento (frase indexada)
        for (Document document : documents) {
            receitas.add(convert(document));
        }
        return receitas;
    }

    public Receita convert(Document document) {
        return new Receita(document.get("id_prato"), document.get("nome_prato"), document.get("nivel_prato"), document.get("id_receita"), document.get("id_fonte_dados"), document.get("url_receita"), document.get("nome_receita"), document.get("tempo_prep_rec"), document.get("rendimento_rec"), document.get("nome_fonte_dados"), document.get("id_categoria_rec"), document.get("nome_categoria_rec"), document.get("id_categoria_prato"), document.get("cat1"), document.get("cat2"), document.get("cat3"), document.get("porc_cat1"), document.get("porc_cat2"), document.get("porc_cat3"), document.get("id_classe_prep_rec"), document.get("classe_assada"), document.get("classe_frita"), document.get("classe_refogada"), document.get("classe_cozida"), document.get("classe_crua"), document.get("id_classe_prep_prato"), document.get("quant_assada"), document.get("porc_assada"), document.get("quant_frita"), document.get("porc_frita"), document.get("quant_refogada"), document.get("porc_refogada"), document.get("quant_cozida"), document.get("porc_cozida"), document.get("quant_crua"), document.get("porc_crua"), document.get("quantidade_ingrediente"), document.get("un_med_ingrediente"), document.get("apelido_un_med_ingrediente"), document.get("nome_ingrediente"), document.get("nome_especifico_ingred"), document.get(" id_instrucao_preparo"), document.get("id_ses_instrucao_preparo"), document.get("instrucao_preparo"), document.get("ordem_instrucao"), document.get("id_sessao_preparo"), document.get("sessao_preparo_nome"), document.get("id_sessao_sentenca"), document.get("sessao_sentenca_nome"));
    }
}