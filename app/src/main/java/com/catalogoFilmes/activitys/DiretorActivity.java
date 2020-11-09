package com.catalogoFilmes.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.aps2020.CatalogoFilmes.R;
import com.catalogoFilmes.adapters.AdapterListaDiretores;
import com.catalogoFilmes.configs.RetrofitConfig;
import com.catalogoFilmes.model.DiretorVO;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.catalogoFilmes.configs.GTRSharedPreferences.REQUEST_CODE_ADICIONAR_EDITAR;

public class DiretorActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AppCompatImageView ivAdicionar;
    private RecyclerView rvLista;
    private SwipeRefreshLayout swRefresh;

    // Container Erro
    private View viewErro;
    private AppCompatTextView tvErro;
    private ContentLoadingProgressBar pbErro;

    private boolean httpRequestIsSucess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diretor);

        intanciarComponentes();

        setListeners();

        getValores();
    }

    private void intanciarComponentes() {
        toolbar = findViewById(R.id.activityDiretor_toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_close_32);

        ivAdicionar = findViewById(R.id.activityDiretor_ivAdicionar);

        // Instanciando SwipeRefresh
        swRefresh = findViewById(R.id.activityDiretor_swipeRefresh);
        swRefresh.setColorSchemeResources(R.color.colorAccent);

        // Instanciando View
        viewErro = findViewById(R.id.activityDiretor_containerErro);

        // Isntanciando Componentes do View Stub
        tvErro = findViewById(R.id.listaErro_tvErro);
        pbErro = findViewById(R.id.listaErro_pbErro);

        rvLista = findViewById(R.id.activityDiretor_rvLista);
        rvLista.setHasFixedSize(true);
        rvLista.setItemViewCacheSize(20);
        rvLista.setDrawingCacheEnabled(true);
        rvLista.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }

    private void setListeners() {
        ivAdicionar.setOnClickListener(getOnClickAdicionar());
        swRefresh.setOnRefreshListener(onRefreshLista());

        // Container Erro
        viewErro.setOnClickListener(getOnClickErro());
    }

    private View.OnClickListener getOnClickAdicionar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiretorActivity.this, AdicionarEditarFilmesActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADICIONAR_EDITAR);
            }
        };
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshLista() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swRefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getValores();
                        swRefresh.setRefreshing(false);
                    }
                }, 2000);
            }
        };
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
        Call<ArrayList<DiretorVO>> call = new RetrofitConfig().getDiretorService().buscarTodosDiretores();
        call.enqueue(new Callback<ArrayList<DiretorVO>>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call<ArrayList<DiretorVO>> call, @NonNull Response<ArrayList<DiretorVO>> response) {
                swRefresh.setVisibility(View.VISIBLE);
                ivAdicionar.setVisibility(View.VISIBLE);
                viewErro.setVisibility(View.GONE);
                if (response.body() != null) {
                    toolbar.setTitle(String.format("%s (%d)", getResources().getString(R.string.diretores), response.body().size()));
                    AdapterListaDiretores adpter = new AdapterListaDiretores(response.body());
                    rvLista.setAdapter(adpter);
                }
                httpRequestIsSucess = true;
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<DiretorVO>> call, @NonNull Throwable t) {
                swRefresh.setVisibility(View.GONE);
                ivAdicionar.setVisibility(View.GONE);
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