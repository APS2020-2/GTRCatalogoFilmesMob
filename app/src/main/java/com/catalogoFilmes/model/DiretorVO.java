package com.catalogoFilmes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DiretorVO implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("dataNasc")
    @Expose
    private String dataNasc;
    @SerializedName("imagem")
    @Expose
    private String imagem;
    @SerializedName("filmes")
    @Expose
    private List<FilmeVO> filmes = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public List<FilmeVO> getFilmes() {
        return filmes;
    }

    public void setFilmes(List<FilmeVO> filmes) {
        this.filmes = filmes;
    }

}