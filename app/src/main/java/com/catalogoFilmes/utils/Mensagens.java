package com.catalogoFilmes.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.aps2020.CatalogoFilmes.R;
import com.catalogoFilmes.componentes.ButtomCustom;
import com.catalogoFilmes.configs.Application;

public class Mensagens {

    public static Dialog DialogBox(String titulo, String mensagem, DialogInterface.OnClickListener Positive, DialogInterface.OnClickListener Negative,
                                   View layout, boolean noButtons) {

        AlertDialog.Builder alert = new AlertDialog.Builder(Application.appContext);
        alert.setTitle(titulo);
        alert.setCancelable(false);

        if (mensagem != null && !mensagem.equals(""))
            alert.setMessage(mensagem);
        alert.setView(layout);

        if (Positive != null) {
            alert.setPositiveButton("OK", Positive);
        }

        if (Negative != null) {
            alert.setNegativeButton("Cancelar", Negative);
        }

        /**
         * Se os dois botões forem passados como null, cria um botão neutro de OK
         */
        if (Positive == null && Negative == null && !noButtons) {
            alert.setPositiveButton("OK", null);
        }

        return alert.create();
    }

    public static class DialogCustom {

        Dialog dialog;

        public void create(Context context, Drawable imTitulo, String titulo,
                           String mensagem, boolean usa2Botoes, boolean usaBotoes,
                           String txtBtEsquerda, String txtBtnDireita, String txtBtnUnico,
                           View.OnClickListener onClickListenerEsquerda,
                           View.OnClickListener onClickListenerDireita,
                           View.OnClickListener onClickListenerUnico) {

            View layout = Application.appActivity.getLayoutInflater().inflate(context.getResources().getLayout(R.layout.component_dialog_custom), null, false);
            // Instânciando Componentes
            AppCompatImageView ivTitulo = layout.findViewById(R.id.dialogCustom_icTitulo);
            AppCompatTextView tvTitulo = layout.findViewById(R.id.dialogCustom_tvTitulo);
            AppCompatTextView tvMensagem = layout.findViewById(R.id.dialogCustom_tvMensagem);
            View divider = layout.findViewById(R.id.dialogCustom_divider2);
            LinearLayoutCompat containerProgress = layout.findViewById(R.id.dialogCustom_containerProgress);
            LinearLayoutCompat container2Botoes = layout.findViewById(R.id.dialogCustom_container2Botoes);
            ButtomCustom btEsquerda = layout.findViewById(R.id.dialogCustom_btEsquerda);
            ButtomCustom btDireita = layout.findViewById(R.id.dialogCustom_btDireita);
            ButtomCustom btUnico = layout.findViewById(R.id.dialogCustom_btUnico);

            // Criando o AlertDialog
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setCancelable(false);
            alert.setView(layout);

            // Atribuindo Valores
            if (imTitulo != null)
                ivTitulo.setImageDrawable(imTitulo);
            else
                ivTitulo.setVisibility(View.GONE);


            if (titulo != null && !"".equals(titulo))
                tvTitulo.setText(titulo);

            if (usaBotoes) {
                divider.setVisibility(View.VISIBLE);

                if (mensagem != null && !"".equals(mensagem))
                    tvMensagem.setText(mensagem);

                container2Botoes.setVisibility(usa2Botoes ? View.VISIBLE : View.GONE);
                btUnico.setVisibility(!usa2Botoes ? View.VISIBLE : View.GONE);

                btUnico.setTextSize(context.getResources().getDimension(R.dimen.font12));

                if (usa2Botoes) {
                    if (txtBtEsquerda != null && !"".equals(txtBtEsquerda))
                        btEsquerda.setText(txtBtEsquerda);
                    if (onClickListenerEsquerda != null)
                        btEsquerda.setOnClickListener(onClickListenerEsquerda);

                    if (txtBtnDireita != null && !"".equals(txtBtnDireita))
                        btDireita.setText(txtBtnDireita);
                    if (onClickListenerDireita != null)
                        btDireita.setOnClickListener(onClickListenerDireita);
                } else {
                    if (txtBtnUnico != null && !"".equals(txtBtnUnico))
                        btUnico.setText(txtBtnUnico);
                    if (onClickListenerUnico != null)
                        btUnico.setOnClickListener(onClickListenerUnico);
                }
            } else {
                tvMensagem.setVisibility(View.GONE);
                divider.setVisibility(View.GONE);
                containerProgress.setVisibility(View.VISIBLE);
            }

            dialog = alert.create();
        }

        public void show() {
            dialog.show();
        }

        public void dismiss() {
            dialog.dismiss();
        }
    }
}