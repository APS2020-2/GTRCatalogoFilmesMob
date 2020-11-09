package com.catalogoFilmes.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.aps2020.CatalogoFilmes.R;
import com.catalogoFilmes.model.GeneroVO;

import java.util.List;

public class AdapterListaGeneros extends RecyclerView.Adapter<AdapterListaGeneros.MyViewHolder> {

    private List<GeneroVO> listaGeneros;

    public AdapterListaGeneros(List<GeneroVO> listaGeneros) {
        this.listaGeneros = listaGeneros;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_generos, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.onBind(listaGeneros, position);
    }

    @Override
    public int getItemCount() {
        return listaGeneros.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView tvNomeGenero;
        private AppCompatTextView tvQtdFilmes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomeGenero = itemView.findViewById(R.id.itemListaGeneros_tvNome);
            tvQtdFilmes = itemView.findViewById(R.id.itemListaGeneros_tvQtdFilmes);
        }

        @SuppressLint("DefaultLocale")
        public void onBind(List<GeneroVO> listaGeneros, int position) {
            GeneroVO generoAtual = listaGeneros.get(position);

            tvNomeGenero.setText(generoAtual.getNome());
            tvQtdFilmes.setText(String.valueOf(generoAtual.getFilmes().size()));

        }
    }
}
