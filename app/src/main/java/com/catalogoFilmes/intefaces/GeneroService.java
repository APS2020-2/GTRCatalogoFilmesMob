package com.catalogoFilmes.intefaces;

import com.catalogoFilmes.model.GeneroVO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GeneroService {

    @GET("genero")
    Call<ArrayList<GeneroVO>> buscarTodosGeneros();

    @GET("genero/{idGenero}")
    Call<GeneroVO> buscarGeneroById(@Path("id") int idGenero);

}
