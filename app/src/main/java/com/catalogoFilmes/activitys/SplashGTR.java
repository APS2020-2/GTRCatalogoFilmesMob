package com.catalogoFilmes.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.aps2020.CatalogoFilmes.R;
import com.catalogoFilmes.configs.Application;

@SuppressWarnings("deprecation")
public class SplashGTR extends AppCompatActivity {
    //Timer da Splash Screen
    @SuppressWarnings("FieldCanBeLocal")
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_gtr);

        Application.appActivity = SplashGTR.this;
        Application.appContext = SplashGTR.this;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Esse método será executado sempre que o timer acabar
                // E inicia a activity principal
                Intent i = new Intent(Application.appContext, PaginaInicial.class);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(Application.appContext, R.anim.fade_in, R.anim.mover_direita);
                ActivityCompat.startActivity(Application.appContext, i, optionsCompat.toBundle());

                // Fecha esta activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}