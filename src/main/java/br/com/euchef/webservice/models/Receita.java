package br.com.euchef.webservice.models;

public class Receita {

    private String id_prato;
    private String nome_prato;
    private String nivel_prato;
    private String id_receita;
    private String id_fonte_dados;
    private String url_receita;
    private String nome_receita;
    private String tempo_prep_rec;
    private String rendimento_rec;
    private String nome_fonte_dados;
    private String id_categoria_rec;
    private String nome_categoria_rec;
    private String id_categoria_prato;
    /*private String nome_categoria_prato;
    private String porc_categoria_prato;*/
    private String cat1;
    private String cat2;
    private String cat3;
    private String porc_cat1;
    private String porc_cat2;
    private String porc_cat3;
    private String id_classe_prep_rec;
    private String classe_assada;
    private String classe_frita;
    private String classe_refogada;
    private String classe_cozida;
    private String classe_crua;
    private String id_classe_prep_prato;
    private String quant_assada;
    private String porc_assada;
    private String quant_frita;
    private String porc_frita;
    private String quant_refogada;
    private String porc_refogada;
    private String quant_cozida;
    private String porc_cozida;
    private String quant_crua;
    private String porc_crua;
    private String quantidade_ingrediente;
    private String un_med_ingrediente;
    private String apelido_un_med_ingrediente;
    private String nome_ingrediente;
    private String nome_especifico_ingred;
    private String id_instrucao_preparo;
    private String id_ses_instrucao_preparo;
    private String instrucao_preparo;
    private String ordem_instrucao;
    private String id_sessao_preparo;
    private String sessao_preparo_nome;
    private String id_sessao_sentenca;
    private String sessao_sentenca_nome;


    public Receita(String id_prato, String nome_prato, String nivel_prato, String id_receita, String id_fonte_dados, String url_receita, String nome_receita, String tempo_prep_rec, String rendimento_rec, String nome_fonte_dados, String id_categoria_rec, String nome_categoria_rec, String id_categoria_prato, String cat1, String cat2, String cat3, String porc_cat1, String porc_cat2, String porc_cat3, String id_classe_prep_rec, String classe_assada, String classe_frita, String classe_refogada, String classe_cozida, String classe_crua, String id_classe_prep_prato, String quant_assada, String porc_assada, String quant_frita, String porc_frita, String quant_refogada, String porc_refogada, String quant_cozida, String porc_cozida, String quant_crua, String porc_crua, String quantidade_ingrediente, String un_med_ingrediente, String apelido_un_med_ingrediente, String nome_ingrediente, String nome_especifico_ingred, String id_instrucao_preparo, String id_ses_instrucao_preparo, String instrucao_preparo, String ordem_instrucao, String id_sessao_preparo, String sessao_preparo_nome, String id_sessao_sentenca, String sessao_sentenca_nome) {
        this.id_prato = id_prato;
        this.nome_prato = nome_prato;
        this.nivel_prato = nivel_prato;
        this.id_receita = id_receita;
        this.id_fonte_dados = id_fonte_dados;
        this.url_receita = url_receita;
        this.nome_receita = nome_receita;
        this.tempo_prep_rec = tempo_prep_rec;
        this.rendimento_rec = rendimento_rec;
        this.nome_fonte_dados = nome_fonte_dados;
        this.id_categoria_rec = id_categoria_rec;
        this.nome_categoria_rec = nome_categoria_rec;
        this.id_categoria_prato = id_categoria_prato;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.cat3 = cat3;
        this.porc_cat1 = porc_cat1;
        this.porc_cat2 = porc_cat2;
        this.porc_cat3 = porc_cat3;
        this.id_classe_prep_rec = id_classe_prep_rec;
        this.classe_assada = classe_assada;
        this.classe_frita = classe_frita;
        this.classe_refogada = classe_refogada;
        this.classe_cozida = classe_cozida;
        this.classe_crua = classe_crua;
        this.id_classe_prep_prato = id_classe_prep_prato;
        this.quant_assada = quant_assada;
        this.porc_assada = porc_assada;
        this.quant_frita = quant_frita;
        this.porc_frita = porc_frita;
        this.quant_refogada = quant_refogada;
        this.porc_refogada = porc_refogada;
        this.quant_cozida = quant_cozida;
        this.porc_cozida = porc_cozida;
        this.quant_crua = quant_crua;
        this.porc_crua = porc_crua;
        this.quantidade_ingrediente = quantidade_ingrediente;
        this.un_med_ingrediente = un_med_ingrediente;
        this.apelido_un_med_ingrediente = apelido_un_med_ingrediente;
        this.nome_ingrediente = nome_ingrediente;
        this.nome_especifico_ingred = nome_especifico_ingred;
        this.id_instrucao_preparo = id_instrucao_preparo;
        this.id_ses_instrucao_preparo = id_ses_instrucao_preparo;
        this.instrucao_preparo = instrucao_preparo;
        this.ordem_instrucao = ordem_instrucao;
        this.id_sessao_preparo = id_sessao_preparo;
        this.sessao_preparo_nome = sessao_preparo_nome;
        this.id_sessao_sentenca = id_sessao_sentenca;
        this.sessao_sentenca_nome = sessao_sentenca_nome;
    }

    public String getId_prato() {
        return id_prato;
    }

    public String getNome_prato() {
        return nome_prato;
    }

    public String getNivel_prato() {
        return nivel_prato;
    }

    public String getId_receita() {
        return id_receita;
    }

    public String getId_fonte_dados() {
        return id_fonte_dados;
    }

    public String getUrl_receita() {
        return url_receita;
    }

    public String getNome_receita() {
        return nome_receita;
    }

    public String getTempo_prep_rec() {
        return tempo_prep_rec;
    }

    public String getRendimento_rec() {
        return rendimento_rec;
    }

    public String getNome_fonte_dados() {
        return nome_fonte_dados;
    }

    public String getId_categoria_rec() {
        return id_categoria_rec;
    }

    public String getNome_categoria_rec() {
        return nome_categoria_rec;
    }

    public String getId_categoria_prato() {
        return id_categoria_prato;
    }


    public String getCat1() {
        return cat1;
    }

    public String getCat2() {
        return cat2;
    }

    public String getCat3() {
        return cat3;
    }

    public String getPorc_cat1() {
        return porc_cat1;
    }

    public String getPorc_cat2() {
        return porc_cat2;
    }

    public String getPorc_cat3() {
        return porc_cat3;
    }

	/*public String getNome_categoria_prato(){
        return nome_categoria_prato;
	}*/

	/*public String getPorc_categoria_prato(){
        return porc_categoria_prato;
	}*/

    public String getId_classe_prep_rec() {
        return id_classe_prep_rec;
    }

    public String getClasse_assada() {
        return classe_assada;
    }

    public String getClasse_frita() {
        return classe_frita;
    }

    public String getClasse_refogada() {
        return classe_refogada;
    }

    public String getClasse_cozida() {
        return classe_cozida;
    }

    public String getClasse_crua() {
        return classe_crua;
    }

    public String getId_classe_prep_prato() {
        return id_classe_prep_prato;
    }

    public String getQuant_assada() {
        return quant_assada;
    }

    public String getPorc_assada() {
        return porc_assada;
    }

    public String getQuant_frita() {
        return quant_frita;
    }

    public String getPorc_frita() {
        return porc_frita;
    }

    public String getQuant_refogada() {
        return quant_refogada;
    }

    public String getPorc_refogada() {
        return porc_refogada;
    }

    public String getQuant_cozida() {
        return quant_cozida;
    }

    public String getPorc_cozida() {
        return porc_cozida;
    }

    public String getQuant_crua() {
        return quant_crua;
    }

    public String getPorc_crua() {
        return porc_crua;
    }

    public String getQuantidade_ingrediente() {
        return quantidade_ingrediente;
    }

    public String getUn_med_ingrediente() {
        return un_med_ingrediente;
    }

    public String getApelido_un_med_ingrediente() {
        return apelido_un_med_ingrediente;
    }

    public String getNome_ingrediente() {
        return nome_ingrediente;
    }

    public String getNome_especifico_ingred() {
        return nome_especifico_ingred;
    }

    public String getId_instrucao_preparo() {
        return id_instrucao_preparo;
    }

    public String getId_ses_instrucao_preparo() {
        return id_ses_instrucao_preparo;
    }

    public String getInstrucao_preparo() {
        return instrucao_preparo;
    }

    public String getOrdem_instrucao() {
        return ordem_instrucao;
    }

    public String getId_sessao_preparo() {
        return id_sessao_preparo;
    }

    public String getSessao_preparo_nome() {
        return sessao_preparo_nome;
    }

    public String getId_sessao_sentenca() {
        return id_sessao_sentenca;
    }

    public String getSessao_sentenca_nome() {
        return sessao_sentenca_nome;
    }


}
