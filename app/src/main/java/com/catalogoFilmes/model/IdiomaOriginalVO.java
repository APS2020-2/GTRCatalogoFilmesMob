package com.catalogoFilmes.model;

public class IdiomaOriginalVO {

    private String CodigoIdioma;
    private String DescricaoIdioma;

    public IdiomaOriginalVO(String codigoIdioma, String descricaoIdioma) {
        CodigoIdioma = codigoIdioma;
        DescricaoIdioma = descricaoIdioma;
    }

    public String getCodigoIdioma() {
        return CodigoIdioma;
    }

    public void setCodigoIdioma(String codigoIdioma) {
        CodigoIdioma = codigoIdioma;
    }

    public String getDescricaoIdioma() {
        return DescricaoIdioma;
    }

    public void setDescricaoIdioma(String descricaoIdioma) {
        DescricaoIdioma = descricaoIdioma;
    }

    @Override
    public String toString() {
        return "IdiomaOriginalVO{" +
                "CodigoIdioma='" + CodigoIdioma + '\'' +
                ", DescricaoIdioma='" + DescricaoIdioma + '\'' +
                '}';
    }
}
