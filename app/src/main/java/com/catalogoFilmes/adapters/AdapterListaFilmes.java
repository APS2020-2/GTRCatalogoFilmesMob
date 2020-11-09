package com.catalogoFilmes.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.recyclerview.widget.RecyclerView;

import com.aps2020.CatalogoFilmes.R;
import com.catalogoFilmes.activitys.AdicionarEditarFilmesActivity;
import com.catalogoFilmes.componentes.MenuRecyclerAdapter;
import com.catalogoFilmes.componentes.expansion.ExpansionLayout;
import com.catalogoFilmes.componentes.expansion.ExpansionLayoutCollection;
import com.catalogoFilmes.componentes.expansion.ExpansionPanel;
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

public class AdapterListaFilmes extends RecyclerView.Adapter<AdapterListaFilmes.MyViewHolder> implements Serializable {

    private Context context;
    private ArrayList<FilmeVO> listaFilmes;
    private ExpansionLayoutCollection expansionsCollection = new ExpansionLayoutCollection();

    public AdapterListaFilmes(Context context, ArrayList<FilmeVO> listaFilmes) {
        this.context = context;
        this.listaFilmes = listaFilmes;
        this.expansionsCollection.openOnlyOne(true);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_lista_filmes, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ExpansionLayout expLayout = ((ExpansionPanel) holder.itemView).getExpLayout();
        expansionsCollection.add(expLayout);
        holder.onBind(listaFilmes, position);
    }

    @Override
    public int getItemCount() {
        return listaFilmes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView tvNomeFilme;
        private AppCompatTextView tvGenero;
        private AppCompatTextView tvDuracao;
        private AppCompatTextView tvDataLancamento;
        private AppCompatTextView tvIdiomaOriginal;
        private AppCompatTextView tvDiretor;
        private AppCompatTextView tvDescricao;

        private AppCompatImageView ivMenu;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomeFilme = itemView.findViewById(R.id.itemListaFilmes_tvValorNomeFilme);
            tvGenero = itemView.findViewById(R.id.itemListaFilmes_tvValorGenero);
            tvDuracao = itemView.findViewById(R.id.itemListaFilmes_tvValorDuracao);
            tvDataLancamento = itemView.findViewById(R.id.itemListaFilmes_tvValorDataLancamento);
            tvIdiomaOriginal = itemView.findViewById(R.id.itemListaFilmes_tvValorIdiomaOriginal);
            tvDiretor = itemView.findViewById(R.id.itemListaFilmes_tvValorDiretor);
            tvDescricao = itemView.findViewById(R.id.expandItemLista_tvValor);

            ivMenu = itemView.findViewById(R.id.itemListaFilmes_ivMenu);
        }

        @SuppressLint("DefaultLocale")
        public void onBind(ArrayList<FilmeVO> listaFilmes, final int positionLista) {
            final FilmeVO filmeAtual = listaFilmes.get(positionLista);

            itemView.setTag(filmeAtual);

            tvNomeFilme.setText(filmeAtual.getTitulo());
            tvGenero.setText(filmeAtual.getGenero().getNome());
            tvDuracao.setText(String.format("%d Min", filmeAtual.getDuracao()));
            tvDataLancamento.setText(filmeAtual.getDataLancamento());
            tvIdiomaOriginal.setText(filmeAtual.getIdiomaOriginal());
            tvDiretor.setText(filmeAtual.getDiretor().getNome());
            tvDescricao.setText(filmeAtual.getDescricao());

            ivMenu.setOnClickListener(null);
            ivMenu.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("UseCompatLoadingForDrawables")
                @Override
                public void onClick(View view) {
                    float scale = context.getResources().getDisplayMetrics().density;

                    MenuRecyclerAdapter menuAdapter = new MenuRecyclerAdapter(context);
                    menuAdapter.add(R.drawable.ic_baseline_create_24, R.string.editar_filme);
                    menuAdapter.add(R.drawable.ic_lixeira_24, R.string.excluir_filme);

                    final ListPopupWindow listPopupWindow = new ListPopupWindow(context);
                    listPopupWindow.setAdapter(menuAdapter);
                    listPopupWindow.setAnchorView(ivMenu);
                    listPopupWindow.setListSelector(context.getResources().getDrawable(R.drawable.selector_recycler_transparent));
                    listPopupWindow.setWidth((int) (200 * scale + 0.5f));
                    listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            if (position == 0)
                                eventoEditar(filmeAtual);
                            else if (position == 1)
                                eventoExcluir(filmeAtual, positionLista);

                            listPopupWindow.dismiss();
                        }
                    });

                    listPopupWindow.setModal(true);
                    listPopupWindow.getBackground().setAlpha(0);
                    listPopupWindow.show();
                }
            });

        }

        private void eventoEditar(FilmeVO filmeAtual) {
            Intent intent = new Intent(context, AdicionarEditarFilmesActivity.class);
            intent.putExtra("filmeEdicao", filmeAtual);
            Application.appActivity.startActivityForResult(intent, REQUEST_CODE_ADICIONAR_EDITAR);
        }

        private void eventoExcluir(final FilmeVO filmeAtual, final int position) {
            final Mensagens.DialogCustom dialogCustom = new Mensagens.DialogCustom();
            dialogCustom.create(context,
                    AppCompatResources.getDrawable(context, R.drawable.ic_round_warning_24),
                    context.getResources().getString(R.string.confirmacao),
                    context.getResources().getString(R.string.deseja_excluir_este_item),
                    true, true, context.getResources().getString(R.string.sim),
                    context.getResources().getString(R.string.nao), null,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Call<Void> callDelete = new RetrofitConfig().getFilmesService().deletarFilme(filmeAtual.getId());
                            callDelete.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                    notifyItemRemoved(position);
                                    listaFilmes.remove(position);
                                    dialogCustom.dismiss();
                                }

                                @Override
                                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                                    listaFilmes.remove(position);
                                    notifyItemInserted(position);
                                }
                            });
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
}
