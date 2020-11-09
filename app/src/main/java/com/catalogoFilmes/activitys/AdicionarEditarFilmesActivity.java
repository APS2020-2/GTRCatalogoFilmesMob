package com.catalogoFilmes.activitys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.aps2020.CatalogoFilmes.R;
import com.catalogoFilmes.componentes.SpinnerCustom;
import com.catalogoFilmes.configs.Application;
import com.catalogoFilmes.configs.RetrofitConfig;
import com.catalogoFilmes.model.DiretorVO;
import com.catalogoFilmes.model.FilmePostVO;
import com.catalogoFilmes.model.FilmePutVO;
import com.catalogoFilmes.model.FilmeVO;
import com.catalogoFilmes.model.GeneroVO;
import com.catalogoFilmes.model.IdiomaOriginalVO;
import com.catalogoFilmes.utils.GTRUtils;
import com.catalogoFilmes.utils.Mensagens;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ConstantConditions")
public class AdicionarEditarFilmesActivity extends AppCompatActivity implements Serializable {

    private FilmePostVO filme;
    private FilmeVO filmeEdicao;
    private boolean isEdicao;
    private Context context;
    private Mensagens.DialogCustom dialogCustom;

    private AppCompatTextView tvTitulo;

    private View view;

    private AppCompatButton btSalvar;
    private AppCompatButton btCancelar;

    private AppCompatEditText etTitulo;
    private AppCompatEditText etDuracao;
    private AppCompatEditText etDataLancamento;
    private AppCompatEditText etDescricao;

    private SpinnerCustom spGenero;
    private SpinnerCustom spIdiomaOriginal;
    private SpinnerCustom spDiretor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_editar);

        context = AdicionarEditarFilmesActivity.this;
        Application.appActivity = this;

        isEdicao = getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey("filmeEdicao");

        filme = new FilmePostVO();

        dialogCustom = new Mensagens.DialogCustom();
        dialogCustom.create(context,
                AppCompatResources.getDrawable(context, R.drawable.ic_round_warning_24),
                context.getResources().getString(R.string.carregando_dados),
                null, false, false, null,
                null, null, null,
                null, null);

        dialogCustom.show();

        intanciarComponentes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populaSpinners();
        setListeners();
    }

    private void intanciarComponentes() {
        Toolbar toolbar = findViewById(R.id.activityAdicionarEditar_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_close_32);

        tvTitulo = findViewById(R.id.activityAdicionarEditar_tvTitulo);
        tvTitulo.setText(getResources().getString(R.string.adicionar_filme));

        view = findViewById(R.id.activityAdicionarEditar_viewStub);

        etTitulo = findViewById(R.id.vsAddEditar_etTitulo);
        etDuracao = findViewById(R.id.vsAddEditar_etDuracao);
        etDataLancamento = findViewById(R.id.vsAddEditar_etDataLancamento);
        etDescricao = findViewById(R.id.vsAddEditar_etDescricao);

        spGenero = findViewById(R.id.vsAddEditar_spGenero);
        spIdiomaOriginal = findViewById(R.id.vsAddEditar_spIdioma);
        spDiretor = findViewById(R.id.vsAddEditar_spDiretor);

        btSalvar = findViewById(R.id.activityAdicionarEditar_btSalvar);
        btCancelar = findViewById(R.id.activityAdicionarEditar_btCancelar);
    }

    private void populaSpinners() {
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                populaSpinnerGenero();
                populaSpinnerIdioma();
                populaSpinnerDiretor();
                dialogCustom.dismiss();

                if (isEdicao) {
                    filmeEdicao = (FilmeVO) getIntent().getExtras().getSerializable("filmeEdicao");
                    carregarValores(filmeEdicao);
                }
            }
        }, 4000);
    }

    private void populaSpinnerGenero() {
        Call<ArrayList<GeneroVO>> callGeneros = new RetrofitConfig().getGeneroService().buscarTodosGeneros();
        callGeneros.enqueue(new Callback<ArrayList<GeneroVO>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<GeneroVO>> call, @NonNull Response<ArrayList<GeneroVO>> response) {
                if (response.body() != null)
                    GTRUtils.SpinnerPopulate(context, spGenero, GTRUtils.generoVOToHashMapArray(response.body()), "DescricaoGenero", "DescricaoGenero");
                if (isEdicao && filmeEdicao != null)
                    GTRUtils.buscaPosicaoSpinner(spGenero, filmeEdicao.getGeneroId().toString(), "CodigoGenero");
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<GeneroVO>> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.erro_recuperar_generos), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populaSpinnerIdioma() {
        ArrayList<IdiomaOriginalVO> listaIdiomas = new ArrayList<>();
        listaIdiomas.add(new IdiomaOriginalVO("EN", "Inglês (EUA)"));
        listaIdiomas.add(new IdiomaOriginalVO("ES", "Espanhol"));

        GTRUtils.SpinnerPopulate(context, spIdiomaOriginal, GTRUtils.idiomaOriginalVOToHashMapArray(listaIdiomas), "DescricaoIdioma", "DescricaoIdioma");
    }

    private void populaSpinnerDiretor() {
        Call<ArrayList<DiretorVO>> callDiretores = new RetrofitConfig().getDiretorService().buscarTodosDiretores();
        callDiretores.enqueue(new Callback<ArrayList<DiretorVO>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<DiretorVO>> call, @NonNull Response<ArrayList<DiretorVO>> response) {
                if (response.body() != null)
                    GTRUtils.SpinnerPopulate(context, spDiretor, GTRUtils.diretorVOToHashMapArray(response.body()), "DescricaoDiretor", "DescricaoDiretor");
                if (isEdicao && filmeEdicao != null)
                    GTRUtils.buscaPosicaoSpinner(spDiretor, filmeEdicao.getDiretorId().toString(), "CodigoDiretor");
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<DiretorVO>> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.erro_recuperar_diretores), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setListeners() {
        btSalvar.setOnClickListener(getOnClickSalvar());
        btCancelar.setOnClickListener(getOnClickCancelar());

    }

    private View.OnClickListener getOnClickSalvar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos())
                    salvarCampos();
            }
        };
    }

    private boolean validarCampos() {
        if (etTitulo != null && "".equals(etTitulo.getText().toString())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                etTitulo.setTooltipText(context.getResources().getString(R.string.digite_titulo_continuar));
            } else
                Toast.makeText(context, context.getResources().getString(R.string.digite_titulo_continuar), Toast.LENGTH_SHORT).show();
            etTitulo.setFocusable(true);

            return false;
        }

        if (spGenero != null && spGenero.getSelectedItemPosition() == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                spGenero.setTooltipText(context.getResources().getString(R.string.escolha_um_genero_valido));
            } else
                Toast.makeText(context, context.getResources().getString(R.string.escolha_uma_opcao_valida), Toast.LENGTH_SHORT).show();

            return false;
        }

        if (etDuracao != null && "".equals(etDuracao.getText().toString()) && "0".equals(etDuracao.getText().toString())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                etDuracao.setTooltipText(context.getResources().getString(R.string.digite_duracao_continuar));
            } else
                Toast.makeText(context, context.getResources().getString(R.string.digite_duracao_continuar), Toast.LENGTH_SHORT).show();
            etDuracao.setFocusable(true);

            return false;
        }

        if (spIdiomaOriginal != null && spIdiomaOriginal.getSelectedItemPosition() == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                spIdiomaOriginal.setTooltipText(context.getResources().getString(R.string.escolha_um_idioma_valido));
            } else
                Toast.makeText(context, context.getResources().getString(R.string.escolha_um_idioma_valido), Toast.LENGTH_SHORT).show();

            return false;
        }

        if (etDataLancamento != null && "".equals(etDataLancamento.getText().toString())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                etDataLancamento.setTooltipText(context.getResources().getString(R.string.digite_data_continuar));
            } else
                Toast.makeText(context, context.getResources().getString(R.string.digite_data_continuar), Toast.LENGTH_SHORT).show();
            etDataLancamento.setFocusable(true);

            return false;
        }

        if (spDiretor != null && spDiretor.getSelectedItemPosition() == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                spDiretor.setTooltipText(context.getResources().getString(R.string.escolha_um_diretor_valido));
            } else
                Toast.makeText(context, context.getResources().getString(R.string.escolha_um_diretor_valido), Toast.LENGTH_SHORT).show();

            return false;
        }

        if (etDescricao != null && "".equals(etDescricao.getText().toString())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                etDescricao.setTooltipText(context.getResources().getString(R.string.digite_descricao_continuar));
            } else
                Toast.makeText(context, context.getResources().getString(R.string.digite_descricao_continuar), Toast.LENGTH_SHORT).show();

            return false;
        }

        return true;
    }

    private void salvarCampos() {
        if (spGenero.getSelectedItemPosition() != 0)
            filme.setGeneroId(Integer.valueOf((String) GTRUtils.buscaSelecionadoSpinner(spGenero, "CodigoGenero")));

        if (spIdiomaOriginal.getSelectedItemPosition() != 0)
            filme.setIdiomaOriginal((String) GTRUtils.buscaSelecionadoSpinner(spIdiomaOriginal, "CodigoIdioma"));

        if (spDiretor.getSelectedItemPosition() != 0)
            filme.setDiretorId(Integer.valueOf((String) GTRUtils.buscaSelecionadoSpinner(spDiretor, "CodigoDiretor")));

        filme.setTitulo(etTitulo.getText() != null ? etTitulo.getText().toString() : "");
        filme.setDuracao(etDuracao.getText() != null ? Integer.parseInt(etDuracao.getText().toString()) : 0);
        filme.setDataLancamento(etDataLancamento.getText() != null ? etDataLancamento.getText().toString() : "");
        filme.setDescricao(etDescricao.getText() != null ? etDescricao.getText().toString() : "");
        filme.setPoster("");

        if (filme != null
                && filme.getTitulo() != null && filme.getDataLancamento() != null
                && filme.getDuracao() != null && filme.getDescricao() != null
                && filme.getGeneroId() != null && filme.getIdiomaOriginal() != null
                && filme.getDiretorId() != null) {

            if (!isEdicao) {
                Call<FilmePostVO> callNovoFilme = new RetrofitConfig().getFilmesService().novoFilme(filme);
                callNovoFilme.enqueue(new Callback<FilmePostVO>() {
                    @Override
                    public void onResponse(@NonNull Call<FilmePostVO> call, @NonNull Response<FilmePostVO> response) {
                        final Mensagens.DialogCustom dialogCustom = new Mensagens.DialogCustom();
                        dialogCustom.create(context,
                                AppCompatResources.getDrawable(context, R.drawable.ic_round_warning_24),
                                context.getResources().getString(R.string.confirmacao),
                                context.getResources().getString(R.string.filme_salvo_sucesso),
                                false, true, null, null,
                                context.getResources().getString(R.string.confirmar),
                                null, null,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        finish();
                                        dialogCustom.dismiss();
                                    }
                                });

                        dialogCustom.show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<FilmePostVO> call, @NonNull Throwable t) {
                        final Mensagens.DialogCustom dialogCustom = new Mensagens.DialogCustom();
                        dialogCustom.create(context,
                                AppCompatResources.getDrawable(context, R.drawable.ic_round_warning_24),
                                context.getResources().getString(R.string.confirmacao),
                                context.getResources().getString(R.string.erro_salvar_filme),
                                false, true, null, null,
                                context.getResources().getString(R.string.confirmar),
                                null, null,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogCustom.dismiss();
                                    }
                                });

                        dialogCustom.show();
                    }
                });
            } else {
                FilmePutVO filmeEditado = new FilmePutVO();
                filmeEditado.setId(filmeEdicao.getId());
                filmeEditado.setTitulo(filme.getTitulo());
                filmeEditado.setDuracao(filme.getDuracao());
                filmeEditado.setDataLancamento(filme.getDataLancamento());
                filmeEditado.setDescricao(filme.getDescricao());
                filmeEditado.setIdiomaOriginal(filme.getIdiomaOriginal());
                filmeEditado.setGeneroId(filme.getGeneroId());
                filmeEditado.setDiretorId(filme.getDiretorId());
                filmeEditado.setPoster(filmeEdicao.getPoster());
                Call<FilmePutVO> callAttFilme = new RetrofitConfig().getFilmesService().atualizarFilme(filmeEdicao.getId(), filmeEditado);
                callAttFilme.enqueue(new Callback<FilmePutVO>() {
                    @Override
                    public void onResponse(@NonNull Call<FilmePutVO> call, @NonNull Response<FilmePutVO> response) {
                        final Mensagens.DialogCustom dialogCustom = new Mensagens.DialogCustom();
                        dialogCustom.create(context,
                                AppCompatResources.getDrawable(context, R.drawable.ic_round_warning_24),
                                context.getResources().getString(R.string.confirmacao),
                                context.getResources().getString(R.string.filme_alterado_sucesso),
                                false, true, null, null,
                                context.getResources().getString(R.string.confirmar),
                                null, null,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        finish();
                                        dialogCustom.dismiss();
                                    }
                                });

                        dialogCustom.show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<FilmePutVO> call, @NonNull Throwable t) {
                        final Mensagens.DialogCustom dialogCustom = new Mensagens.DialogCustom();
                        dialogCustom.create(context,
                                AppCompatResources.getDrawable(context, R.drawable.ic_round_warning_24),
                                context.getResources().getString(R.string.confirmacao),
                                context.getResources().getString(R.string.erro_salvar_filme),
                                false, true, null, null,
                                context.getResources().getString(R.string.confirmar),
                                null, null,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogCustom.dismiss();
                                    }
                                });

                        dialogCustom.show();
                    }
                });
            }
        }
    }

    private View.OnClickListener getOnClickCancelar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fecharTela();
            }
        };
    }

    public void fecharTela() {
        final Mensagens.DialogCustom dialogCustom = new Mensagens.DialogCustom();
        dialogCustom.create(context,
                AppCompatResources.getDrawable(context, R.drawable.ic_round_warning_24),
                context.getResources().getString(R.string.confirmacao),
                context.getResources().getString(R.string.deseja_sair_sem_salvar),
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.nothing, R.anim.slide_in_down);
    }

    @Override
    public void onBackPressed() {
        fecharTela();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            fecharTela();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("SetTextI18n")
    private void carregarValores(FilmeVO filmeEdicao) {
        if (filmeEdicao != null) {
            tvTitulo.setText(context.getResources().getString(R.string.editar_filme));
            etTitulo.setText(filmeEdicao.getTitulo());
            etDuracao.setText(filmeEdicao.getDuracao().toString());
            etDataLancamento.setText(filmeEdicao.getDataLancamento());
            etDescricao.setText(filmeEdicao.getDescricao());

            GTRUtils.buscaPosicaoSpinner(spIdiomaOriginal, filmeEdicao.getIdiomaOriginal(), "CodigoIdioma");
        }
    }
}