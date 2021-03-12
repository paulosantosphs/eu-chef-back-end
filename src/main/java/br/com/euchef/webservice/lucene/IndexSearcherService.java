package br.com.euchef.webservice.lucene;

import br.com.euchef.webservice.properties.EuChefProperties;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class IndexSearcherService {

    private final static String CARACTERE_NIVEL_1 = "@";

    @Autowired
    private EuChefProperties properties;

    private Path indexPath;
    private Directory directory;
    private DirectoryReader reader;
    private IndexSearcher searcher;


    public IndexSearcherService() throws IOException {
    }

    @PostConstruct
    public void init() throws Exception {
        indexPath = Paths.get(properties.getLuceneIndexPath());
        directory = FSDirectory.open(indexPath);
        reader = DirectoryReader.open(directory);
        searcher = new IndexSearcher(reader);
    }


    /**
     * Retorna score
     *
     * @param term
     * @param field
     * @param max
     * @return
     * @throws ParseException
     * @throws CorruptIndexException
     * @throws IOException
     */
    public ScoreDoc[] getScoreDocs(String term, String field, int max)
            throws ParseException, CorruptIndexException, IOException {
        Query query = createQuery(term, field);
        ScoreDoc[] scoreDocs = retrieveScoreDocsFrom(query, max);
        return scoreDocs;
    }

    /**
     * Cria query que sera utilizada pelo IndexSearcher para obtem as receitas
     *
     * @param term
     * @param field
     * @return
     * @throws ParseException
     */
    public Query createQuery(String term, String field) throws ParseException {
        QueryParser parser = new QueryParser(field, new SimpleAnalyzer());
        return parser.parse(term);
    }

    public Query createQueryPratos(String ingredientes, String field) throws ParseException {
        String ings[] = ingredientes.split(CARACTERE_NIVEL_1);
        String fields[] = new String[ings.length];
        BooleanClause.Occur[] occurs = new BooleanClause.Occur[ings.length];
        for (int i = 0; i < ings.length; i++) {
            fields[i] = field;
            occurs[i] = BooleanClause.Occur.MUST;
        }
        Query query = MultiFieldQueryParser.parse(ings, fields, occurs, new SimpleAnalyzer());
        return query;

    }

    public Query createQueryCat(String ingredientes, String fieldIng, String categorias, String fieldCat) throws ParseException {
        String term = ingredientes + CARACTERE_NIVEL_1 + categorias;
        String terms[] = term.split(CARACTERE_NIVEL_1);
        String fields[] = new String[terms.length];
        BooleanClause.Occur[] occurs = new BooleanClause.Occur[terms.length];
        int catNum = categorias.split(CARACTERE_NIVEL_1).length;
        for (int i = 0; i < terms.length; i++) {
            if (i < (terms.length - catNum)) {
                fields[i] = fieldIng;
            } else {
                fields[i] = fieldCat;
            }
            occurs[i] = BooleanClause.Occur.MUST;
        }
        Query query = MultiFieldQueryParser.parse(terms, fields, occurs, new SimpleAnalyzer());
        return query;

    }

    public Query createQueryForTwoFields(String termA, String termB, String fieldA, String fieldB) throws ParseException {
        Query query = MultiFieldQueryParser.parse(
                new String[]{termA, termB},
                new String[]{fieldA, fieldB},
                new BooleanClause.Occur[]{BooleanClause.Occur.MUST, BooleanClause.Occur.MUST},
                new SimpleAnalyzer());
        return query;
    }

    /**
     * Query para filtro
     */

    public Query createQueryForFilter(String nomePrato, String ingredientesDesejados, String ingredientesNaoDesejados, String fieldPrato, String fieldIngrediente) throws ParseException {
        String desejados[] = ingredientesDesejados.split(CARACTERE_NIVEL_1);
        String naoDesejados[] = ingredientesNaoDesejados.split(CARACTERE_NIVEL_1);
        String[] terms = new String[1 + desejados.length + naoDesejados.length];
        String[] fields = new String[1 + desejados.length + naoDesejados.length];
        BooleanClause.Occur[] occurs = new BooleanClause.Occur[1 + desejados.length + naoDesejados.length];
        for (int i = 0; i < 1 + desejados.length + naoDesejados.length; i++) {
            if (i == 0) {
                terms[i] = nomePrato;
                fields[i] = fieldPrato;
                occurs[i] = BooleanClause.Occur.MUST;
            } else if (i <= desejados.length) {
                terms[i] = desejados[i - 1];
                fields[i] = fieldIngrediente;
                occurs[i] = BooleanClause.Occur.MUST;
            } else {
                terms[i] = naoDesejados[i - desejados.length - 1];
                fields[i] = fieldIngrediente;
                occurs[i] = BooleanClause.Occur.MUST_NOT;
            }
        }
        Query query = MultiFieldQueryParser.parse(terms, fields, occurs, new SimpleAnalyzer());
        return query;
    }

    /**
     * Retorna os documentos que se enquadram na query
     *
     * @param query
     * @param max
     * @return
     * @throws CorruptIndexException
     * @throws IOException
     */
    public ScoreDoc[] retrieveScoreDocsFrom(Query query, int max)
            throws CorruptIndexException, IOException {
        // Obtem os N documentos que se enquadram na pesquisa e suas estatisticas
        TopDocs topDocs = this.searcher.search(query, max);

        // Retorna apenas os documentos
        return topDocs.scoreDocs;
    }

    /**
     * Conta quantos documentos possuem o termo no field
     */
    public int getCount(String prato, String field) throws ParseException, IOException {
        Query query = createQuery(prato, field);
        TotalHitCountCollector collector = new TotalHitCountCollector();
        searcher.search(query, collector);
        int count = collector.getTotalHits();
        return count;

    }

    /**
     * Conta quantos documentos possuem o termo no field, para dois fields
     */
    public int getCountForTwoFields(String termA, String termB, String fieldA, String fieldB) throws ParseException, IOException {
        Query query = createQueryForTwoFields(termA, termB, fieldA, fieldB);
        TotalHitCountCollector collector = new TotalHitCountCollector();
        this.searcher.search(query, collector);
        int count = collector.getTotalHits();
        return count;
    }

    /**
     * Conta quantos documentos possuem o termo no field, para dois fields
     */
    public int getCountForFilter(String nomePrato, String ingredientesDesejados, String ingredientesNaoDesejados, String fieldPrato, String fieldIngrediente) throws ParseException, IOException {
        Query query = createQueryForFilter(nomePrato, ingredientesDesejados, ingredientesNaoDesejados, fieldPrato, fieldIngrediente);
        TotalHitCountCollector collector = new TotalHitCountCollector();
        this.searcher.search(query, collector);
        int count = collector.getTotalHits();
        return count;
    }

    public int getCountForPratos(String ingredientes, String field) throws ParseException, IOException {
        Query query = createQueryPratos(ingredientes, field);
        TotalHitCountCollector collector = new TotalHitCountCollector();
        this.searcher.search(query, collector);
        int count = collector.getTotalHits();
        return count;
    }

    public int getCountForCat(String ingredientes, String fieldIng, String categoria, String fieldCat) throws ParseException, IOException {
        Query query = createQueryCat(ingredientes, fieldIng, categoria, fieldCat);
        TotalHitCountCollector collector = new TotalHitCountCollector();
        this.searcher.search(query, collector);
        int count = collector.getTotalHits();
        return count;
    }

    public Document doc(int doc) throws IOException {
        return this.searcher.doc(doc);
    }

}