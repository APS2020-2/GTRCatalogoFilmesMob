package com.catalogoFilmes.intefaces;

import com.catalogoFilmes.model.DiretorVO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DiretorService {

    @GET("diretor")
    Call<ArrayList<DiretorVO>> buscarTodosDiretores();

    @GET("diretor/{idDiretor}")
    Call<DiretorVO> buscarDiretorById(@Path("id") int idDiretor);

}
