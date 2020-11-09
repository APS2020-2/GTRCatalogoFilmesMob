package com.catalogoFilmes.intefaces;

import com.catalogoFilmes.model.FilmePostVO;
import com.catalogoFilmes.model.FilmePutVO;
import com.catalogoFilmes.model.FilmeVO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FilmeService {

    @GET("Filme")
    Call<ArrayList<FilmeVO>> buscarTodosFilmes();

    @GET("Filme/{Filmeid}")
    Call<FilmeVO> buscarFilmeById(@Path("id") int Filmeid);

    @GET("Filme-Genero/{GeneroId}")
    Call<ArrayList<FilmeVO>> buscaFilmePorGenero(@Path("GeneroId") int GeneroId);

    @GET("Filme-Diretor/{DiretorId}")
    Call<ArrayList<FilmeVO>> buscaFilmePorDiretor(@Path("DiretorId") int DiretorId);

    @POST("Filme")
    Call<FilmePostVO> novoFilme(@Body FilmePostVO filme);

    @PUT("Filme/{FilmeId}")
    Call<FilmePutVO> atualizarFilme(@Path("FilmeId") int Filmeid, @Body FilmePutVO filme);

    @DELETE("Filme/{FilmeId}")
    Call<Void> deletarFilme(@Path("FilmeId") int Filmeid);
}
