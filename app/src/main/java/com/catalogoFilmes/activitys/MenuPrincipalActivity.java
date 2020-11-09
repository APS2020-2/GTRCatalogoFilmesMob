package com.catalogoFilmes.activitys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.aps2020.CatalogoFilmes.R;
import com.catalogoFilmes.adapters.AdapterListaFilmes;
import com.catalogoFilmes.configs.Application;
import com.catalogoFilmes.configs.RetrofitConfig;
import com.catalogoFilmes.model.FilmeVO;
import com.catalogoFilmes.utils.Mensagens;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.catalogoFilmes.configs.GTRSharedPreferences.REQUEST_CODE_ADICIONAR_EDITAR;
import static com.catalogoFilmes.configs.GTRSharedPreferences.REQUEST_CODE_DIRETORES;
import static com.catalogoFilmes.configs.GTRSharedPreferences.REQUEST_CODE_GENEROS;

public class MenuPrincipalActivity extends AppCompatActivity implements Serializable {

    private Context context;

    private AppCompatTextView tvTitulo;
    private AppCompatImageView ivAdicionar;
    private SwipeRefreshLayout swRefresh;
    private RecyclerView rvLista;
    private CardView botaoGenero;
    private CardView botaoDiretor;

    // Container Erro
    private View viewErro;
    private AppCompatTextView tvErro;
    private ContentLoadingProgressBar pbErro;

    private boolean httpRequestIsSucess;
    private AdapterListaFilmes adpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        intanciarComponentes();
        setListeners();

        getValores();
    }

    @Override
    protected void onResume() {
        super.onResume();
        context = this;
        Application.appActivity = this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADICIONAR_EDITAR) {
            getValores();
            if (rvLista.getAdapter() != null)
                rvLista.getAdapter().notifyDataSetChanged();
        }
    }

    private void intanciarComponentes() {
        context = this;
        Application.appActivity = this;

        Toolbar toolbar = findViewById(R.id.menuPrincipal_toolbar);
        tvTitulo = findViewById(R.id.menuPrincipal_tvTitulo);
        ivAdicionar = findViewById(R.id.menuPrincipal_ivAdicionar);
        botaoGenero = findViewById(R.id.menuPrincipal_cvGeneros);
        botaoDiretor = findViewById(R.id.menuPrincipal_cvDiretores);

        // Instanciando SwipeRefresh
        swRefresh = findViewById(R.id.menuPrincipal_swipeRefresh);
        swRefresh.setColorSchemeResources(R.color.colorAccent);

        // Instanciando View Stub
        viewErro = findViewById(R.id.menuPrincipal_containerErro);

        // Isntanciando Componentes do View Stub
        tvErro = findViewById(R.id.listaErro_tvErro);
        pbErro = findViewById(R.id.listaErro_pbErro);

        setSupportActionBar(toolbar);

        rvLista = findViewById(R.id.menuPrincipal_rvLista);
        rvLista.setHasFixedSize(true);
        rvLista.setItemViewCacheSize(20);
        rvLista.setDrawingCacheEnabled(true);
        rvLista.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }

    private void setListeners() {
        ivAdicionar.setOnClickListener(getOnClickAdicionar());
        swRefresh.setOnRefreshListener(onRefreshLista());
        botaoGenero.setOnClickListener(getOnClickGenero());
        botaoDiretor.setOnClickListener(getOnClickDiretor());

        // Container Erro
        viewErro.setOnClickListener(getOnClickErro());
    }

    private View.OnClickListener getOnClickAdicionar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipalActivity.this, AdicionarEditarFilmesActivity.class);
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

    private View.OnClickListener getOnClickGenero() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent generoIntent = new Intent(MenuPrincipalActivity.this, GeneroActivity.class);
                startActivityForResult(generoIntent, REQUEST_CODE_GENEROS);
            }
        };
    }

    private View.OnClickListener getOnClickDiretor() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent diretorIntent = new Intent(MenuPrincipalActivity.this, DiretorActivity.class);
                startActivityForResult(diretorIntent, REQUEST_CODE_DIRETORES);
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
        Call<ArrayList<FilmeVO>> call = new RetrofitConfig().getFilmesService().buscarTodosFilmes();
        call.enqueue(new Callback<ArrayList<FilmeVO>>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call<ArrayList<FilmeVO>> call, @NonNull Response<ArrayList<FilmeVO>> response) {
                swRefresh.setVisibility(View.VISIBLE);
                ivAdicionar.setVisibility(View.VISIBLE);
                viewErro.setVisibility(View.GONE);
                if (response.body() != null) {
                    tvTitulo.setText(String.format("%s (%d)", context.getResources().getString(R.string.filmes), response.body().size()));
                    adpter = new AdapterListaFilmes(context, response.body());
                    rvLista.setAdapter(adpter);
                }
                httpRequestIsSucess = true;
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<FilmeVO>> call, @NonNull Throwable t) {
                swRefresh.setVisibility(View.GONE);
                ivAdicionar.setVisibility(View.GONE);
                viewErro.setVisibility(View.VISIBLE);
                httpRequestIsSucess = false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        final Mensagens.DialogCustom dialogCustom = new Mensagens.DialogCustom();
        dialogCustom.create(context,
                AppCompatResources.getDrawable(context, R.drawable.ic_round_warning_24),
                context.getResources().getString(R.string.confirmacao),
                context.getResources().getString(R.string.deseja_sair_do_aplicativo),
                true, true, getResources().getString(R.string.sim),
                getResources().getString(R.string.nao), null,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        dialogCustom.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogCustom.dismiss();
                    }
                }, null);

        dialogCustom.show();
    }
}