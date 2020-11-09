package com.catalogoFilmes.configs;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

@SuppressWarnings("deprecation")
public class GTRSharedPreferences {

    public static final String API_BASE_URL = "http://localhost:5000/CatalogoFilmesAPI/";
    public static final int REQUEST_CODE_GENEROS = 1;
    public static final int REQUEST_CODE_DIRETORES = 2;
    public static final int REQUEST_CODE_ADICIONAR_EDITAR = 3;

    public static String INFO_DARK_MODE = "infoDarkModeVisible";

    public static SharedPreferences sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(Application.appContext);

    public static Boolean getKey(String key, Boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static void save(String key, Object value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (value instanceof String)
            editor.putString(key, (String) value);
        else if (value instanceof Boolean)
            editor.putBoolean(key, (Boolean) value);
        else if (value instanceof Integer)
            editor.putInt(key, (Integer) value);
        else if (value instanceof Long)
            editor.putLong(key, (Long) value);
        editor.apply();
    }
}