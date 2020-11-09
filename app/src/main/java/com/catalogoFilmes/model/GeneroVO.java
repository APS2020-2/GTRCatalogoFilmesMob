package com.catalogoFilmes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GeneroVO implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("filmes")
    @Expose
    private List<Object> filmes = null;

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

    public List<Object> getFilmes() {
        return filmes;
    }

    public void setFilmes(List<Object> filmes) {
        this.filmes = filmes;
    }

}