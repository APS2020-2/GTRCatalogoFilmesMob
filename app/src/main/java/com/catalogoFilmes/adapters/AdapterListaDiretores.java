package com.catalogoFilmes.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.aps2020.CatalogoFilmes.R;
import com.catalogoFilmes.componentes.expansion.ExpansionLayout;
import com.catalogoFilmes.componentes.expansion.ExpansionLayoutCollection;
import com.catalogoFilmes.componentes.expansion.ExpansionPanel;
import com.catalogoFilmes.configs.Application;
import com.catalogoFilmes.model.DiretorVO;

import java.util.List;

public class AdapterListaDiretores extends RecyclerView.Adapter<AdapterListaDiretores.MyViewHolder> {

    private List<DiretorVO> listaDiretores;
    private ExpansionLayoutCollection expansionsCollection = new ExpansionLayoutCollection();

    public AdapterListaDiretores(List<DiretorVO> listaDiretores) {
        this.listaDiretores = listaDiretores;
        this.expansionsCollection.openOnlyOne(true);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_lista_diretores, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ExpansionLayout expLayout = ((ExpansionPanel) holder.itemView).getExpLayout();
        expansionsCollection.add(expLayout);
        holder.onBind(listaDiretores, position);
    }

    @Override
    public int getItemCount() {
        return listaDiretores.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView tvNomeDiretor;
        private AppCompatTextView tvDataNasc;
        private AppCompatTextView tvLabelListaFilmes;
        private AppCompatTextView tvListaFilmes;
        private AppCompatTextView tvLabelBio;
        private AppCompatTextView tvBio;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomeDiretor = itemView.findViewById(R.id.itemListaDiretores_tvValorNomeDiretor);
            tvDataNasc = itemView.findViewById(R.id.itemListaDiretores_tvValorDataNasc);
            tvLabelListaFilmes = itemView.findViewById(R.id.itemListaDiretores_tvLabelListaFilmes);
            tvListaFilmes = itemView.findViewById(R.id.itemListaDiretores_tvValorListaFilmes);

            tvLabelBio = itemView.findViewById(R.id.expandItemLista_tvLabel);
            tvBio = itemView.findViewById(R.id.expandItemLista_tvValor);
        }

        @SuppressLint("DefaultLocale")
        public void onBind(List<DiretorVO> listaDiretores, int position) {
            DiretorVO diretorAtual = listaDiretores.get(position);

            tvNomeDiretor.setText(diretorAtual.getNome());
            tvDataNasc.setText(diretorAtual.getDataNasc());
            tvLabelBio.setText(Application.appContext.getResources().getString(R.string.biografia));
            tvBio.setText(diretorAtual.getBio());

            if (diretorAtual.getFilmes() != null && diretorAtual.getFilmes().size() != 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < diretorAtual.getFilmes().size(); i++) {
                    stringBuilder.append("- ").append(diretorAtual.getFilmes().get(i).getTitulo()).append("\n");
                }
                tvListaFilmes.setText(stringBuilder.toString());
            } else {
                tvLabelListaFilmes.setVisibility(View.GONE);
                tvListaFilmes.setVisibility(View.GONE);
            }
        }
    }
}
