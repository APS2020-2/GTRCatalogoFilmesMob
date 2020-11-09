package com.catalogoFilmes.activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;

import com.aps2020.CatalogoFilmes.R;
import com.catalogoFilmes.configs.Application;
import com.catalogoFilmes.configs.GTRSharedPreferences;
import com.catalogoFilmes.utils.Mensagens;

public class PaginaInicial extends AppCompatActivity {

    AppCompatButton btIniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);

        Application.appActivity = PaginaInicial.this;
        Application.appContext = PaginaInicial.this;

        boolean msgDarkMode = GTRSharedPreferences.getKey(GTRSharedPreferences.INFO_DARK_MODE, true);

        btIniciar = findViewById(R.id.pafinaInicial_btIniciar);
        btIniciar.setTextSize(getResources().getDimension(R.dimen.font8));
        btIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaMenuPrincipal();
            }
        });


        if (Build.VERSION.SDK_INT >= 29 && msgDarkMode)
            abrirAvisoDarkMode();

    }

    private void abrirTelaMenuPrincipal() {
        Intent i = new Intent(Application.appContext, MenuPrincipalActivity.class);
        startActivity(i);
        finish();
    }

    private void abrirAvisoDarkMode() {
        final Mensagens.DialogCustom dialogCustom = new Mensagens.DialogCustom();
        dialogCustom.create(Application.appContext,
                AppCompatResources.getDrawable(Application.appContext, R.drawable.ic_round_warning_24),
                Application.appContext.getResources().getString(R.string.modo_escuro),
                Application.appContext.getResources().getString(R.string.apps_erro_dark_mode),
                false, true, null, null,
                Application.appContext.getResources().getString(R.string.confirmar),
                null, null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GTRSharedPreferences.save(GTRSharedPreferences.INFO_DARK_MODE, false);
                        dialogCustom.dismiss();
                    }
                });

        dialogCustom.show();
    }
}