package com.catalogoFilmes.activitys;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.RecyclerView;

import com.aps2020.CatalogoFilmes.R;
import com.catalogoFilmes.adapters.AdapterListaGeneros;
import com.catalogoFilmes.configs.RetrofitConfig;
import com.catalogoFilmes.model.GeneroVO;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeneroActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rvLista;

    // Container Erro
    private View viewErro;
    private AppCompatTextView tvErro;
    private ContentLoadingProgressBar pbErro;

    private boolean httpRequestIsSucess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genero);

        intanciarComponentes();

        setListeners();

        getValores();
    }

    private void intanciarComponentes() {
        toolbar = findViewById(R.id.activityGenero_toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_close_32);

        // Instanciando View de Erro
        viewErro = findViewById(R.id.activityGenero_containerErro);

        // Isntanciando Componentes do View Stub
        tvErro = findViewById(R.id.listaErro_tvErro);
        pbErro = findViewById(R.id.listaErro_pbErro);

        rvLista = findViewById(R.id.activityGenero_rvLista);
        rvLista.setHasFixedSize(true);
        rvLista.setItemViewCacheSize(20);
        rvLista.setDrawingCacheEnabled(true);
        rvLista.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }

    private void setListeners() {
        // Container Erro
        viewErro.setOnClickListener(getOnClickErro());
    }

    private View.OnClickListener getOnClickErro() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvErro.setVisibility(View.GONE);
                pbErro.setVisibility(View.VISIBLE);
                viewErro.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getValores();
                        if (!httpRequestIsSucess) {
                            pbErro.setVisibility(View.GONE);
                            tvErro.setVisibility(View.VISIBLE);
                        }
                    }
                }, 5000);
            }
        };
    }

    private void getValores() {
        Call<ArrayList<GeneroVO>> call = new RetrofitConfig().getGeneroService().buscarTodosGeneros();
        call.enqueue(new Callback<ArrayList<GeneroVO>>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call<ArrayList<GeneroVO>> call, @NonNull Response<ArrayList<GeneroVO>> response) {
                viewErro.setVisibility(View.GONE);
                rvLista.setVisibility(View.VISIBLE);
                if (response.body() != null) {
                    toolbar.setTitle(String.format("%s (%d)", toolbar.getTitle(), response.body().size()));
                    AdapterListaGeneros adpter = new AdapterListaGeneros(response.body());
                    rvLista.setAdapter(adpter);
                }
                httpRequestIsSucess = true;
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<GeneroVO>> call, @NonNull Throwable t) {
                rvLista.setVisibility(View.GONE);
                viewErro.setVisibility(View.VISIBLE);
                httpRequestIsSucess = false;
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.nothing, R.anim.slide_in_down);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}