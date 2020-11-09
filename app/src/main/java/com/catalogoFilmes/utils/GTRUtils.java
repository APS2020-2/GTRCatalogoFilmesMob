package com.catalogoFilmes.utils;

import android.content.Context;

import com.aps2020.CatalogoFilmes.R;
import com.catalogoFilmes.activitys.IdiomaOriginalVO;
import com.catalogoFilmes.componentes.SpinnerCustom;
import com.catalogoFilmes.model.DiretorVO;
import com.catalogoFilmes.model.GeneroVO;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings({"unchecked", "ConstantConditions"})
public class GTRUtils {

    public static boolean IsNullOrEmpty(String source) {
        return ("".equals(source) || source == null);
    }

    public static void SpinnerPopulate(Context context, SpinnerCustom spinner,
                                       ArrayList<HashMap<String, String>> dados,
                                       String chave, String descricao) {

        HashMap<String, String> hmTodos = new HashMap<>();
        hmTodos.put(chave, context.getResources().getString(R.string.selecione_uma_opcao));
        dados.add(0, hmTodos);

        spinner.setAdapter(dados, descricao);
    }

    public static ArrayList<HashMap<String, String>> idiomaOriginalVOToHashMapArray(ArrayList<IdiomaOriginalVO> lista) {
        ArrayList<HashMap<String, String>> retorno = new ArrayList<>();

        for (int i = 0; i <= lista.size() - 1; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("CodigoIdioma", lista.get(i).getCodigoIdioma());
            map.put("DescricaoIdioma", lista.get(i).getDescricaoIdioma());
            retorno.add(map);
        }

        return retorno;
    }

    public static ArrayList<HashMap<String, String>> generoVOToHashMapArray(ArrayList<GeneroVO> lista) {
        ArrayList<HashMap<String, String>> retorno = new ArrayList<>();

        for (int i = 0; i <= lista.size() - 1; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("CodigoGenero", String.valueOf(lista.get(i).getId()));
            map.put("DescricaoGenero", lista.get(i).getNome());
            retorno.add(map);
        }

        return retorno;
    }

    public static ArrayList<HashMap<String, String>> diretorVOToHashMapArray(ArrayList<DiretorVO> lista) {
        ArrayList<HashMap<String, String>> retorno = new ArrayList<>();

        for (int i = 0; i <= lista.size() - 1; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("CodigoDiretor", String.valueOf(lista.get(i).getId()));
            map.put("DescricaoDiretor", lista.get(i).getNome());
            retorno.add(map);
        }

        return retorno;
    }

    @SuppressWarnings("unchecked")
    public static Object buscaSelecionadoSpinner(SpinnerCustom spinner, String chave) {

        if (spinner.getSelectedItem() instanceof HashMap) {
            return ((HashMap<String, Object>) spinner.getSelectedItem()).get(chave);
        } else if (spinner.getAdapter().getCount() > 0) {
            return spinner.getSelectedItem();
        }

        return null;
    }

    public static boolean buscaPosicaoSpinner(SpinnerCustom spinner, String procurado, String campo) {

        if (spinner != null && spinner.getAdapter() != null && spinner.getAdapter().getCount() > 0) {
            if (spinner.getAdapter().getItem(0) instanceof HashMap) {
                HashMap<String, Object> hashMap;
                for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
                    hashMap = (HashMap<String, Object>) spinner.getAdapter().getItem(i);

                    if (hashMap.get(campo) != null && hashMap.get(campo).equals(procurado)) {
                        spinner.setSelection(i, true);
                        return true;
                    }

                }
            } else {
                for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
                    if (spinner.getAdapter().getItem(i).toString().equals(procurado)) {
                        spinner.setSelection(i, true);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
