package com.catalogoFilmes.componentes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aps2020.CatalogoFilmes.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Thiago Machado on 19/10/2020.
 */

public class MenuRecyclerAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, Integer>> itens;
    private LayoutInflater layoutInflater;

    public MenuRecyclerAdapter(Context context) {
        this.itens = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Object getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.det_items_menu_recycler, viewGroup, false);
            holder = new ViewHolder();

            holder.ivIcon = view.findViewById(R.id.detitemsmenurecycler_ivIcon);
            holder.tvLabel = view.findViewById(R.id.detitemsmenurecycler_tvLabel);

            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();

        holder.ivIcon.setImageResource(itens.get(position).get("Icone"));
        holder.tvLabel.setText(itens.get(position).get("Texto"));

        return view;
    }

    public static class ViewHolder {
        ImageView ivIcon;
        TextView tvLabel;
    }

    public void add(int icone, int label) {
        HashMap<String, Integer> item = new HashMap<>();
        item.put("Icone", icone);
        item.put("Texto", label);
        itens.add(item);
    }
}
