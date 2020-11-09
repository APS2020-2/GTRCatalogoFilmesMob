package com.catalogoFilmes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FilmePostVO implements Serializable {

    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("poster")
    @Expose
    private String poster;
    @SerializedName("descricao")
    @Expose
    private String descricao;
    @SerializedName("idiomaOriginal")
    @Expose
    private String idiomaOriginal;
    @SerializedName("dataLancamento")
    @Expose
    private String dataLancamento;
    @SerializedName("duracao")
    @Expose
    private Integer duracao;
    @SerializedName("generoId")
    @Expose
    private Integer generoId;
    @SerializedName("diretorId")
    @Expose
    private Integer diretorId;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIdiomaOriginal() {
        return idiomaOriginal;
    }

    public void setIdiomaOriginal(String idiomaOriginal) {
        this.idiomaOriginal = idiomaOriginal;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public Integer getGeneroId() {
        return generoId;
    }

    public void setGeneroId(Integer generoId) {
        this.generoId = generoId;
    }

    public Integer getDiretorId() {
        return diretorId;
    }

    public void setDiretorId(Integer diretorId) {
        this.diretorId = diretorId;
    }

    @Override
    public String toString() {
        return "FilmePUTVO{" +
                "titulo='" + titulo + '\'' +
                ", poster='" + poster + '\'' +
                ", descricao='" + descricao + '\'' +
                ", idiomaOriginal='" + idiomaOriginal + '\'' +
                ", dataLancamento='" + dataLancamento + '\'' +
                ", duracao=" + duracao +
                ", generoId=" + generoId +
                ", diretorId=" + diretorId +
                '}';
    }
}
