package com.catalogoFilmes.configs;

import com.catalogoFilmes.intefaces.DiretorService;
import com.catalogoFilmes.intefaces.FilmeService;
import com.catalogoFilmes.intefaces.GeneroService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.catalogoFilmes.configs.GTRSharedPreferences.API_BASE_URL;

@SuppressWarnings("FieldCanBeLocal")
public class RetrofitConfig {

    private final Retrofit retrofit;

    public RetrofitConfig() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        this.retrofit =
                new Retrofit
                        .Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build();
    }

    public FilmeService getFilmesService() {
        return this.retrofit.create(FilmeService.class);
    }

    public GeneroService getGeneroService() {
        return this.retrofit.create(GeneroService.class);
    }

    public DiretorService getDiretorService() {
        return this.retrofit.create(DiretorService.class);
    }
}
