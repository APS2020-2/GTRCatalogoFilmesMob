package com.catalogoFilmes.componentes;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.SpinnerAdapter;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.aps2020.CatalogoFilmes.R;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("FieldCanBeLocal")
public class SpinnerCustom extends LinearLayout {

    private ConstraintLayout container;
    private AppCompatSpinner spinner;
    private AppCompatImageView imageView;

    public SpinnerCustom(Context context) {
        super(context);
        init();
    }

    public SpinnerCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpinnerCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.componente_spinner_custom, this, false);

        container = view.findViewById(R.id.buttondatepicker_container);
        container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
            }
        });

        container.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if (spinner == null || spinner.getChildCount() == 0)
                    return;

                AppCompatTextView textView = (AppCompatTextView) spinner.getChildAt(0);

                textView.setPadding(0,
                        textView.getPaddingTop(),
                        textView.getPaddingRight(),
                        textView.getPaddingBottom());

            }
        });

        if (spinner != null && spinner.getChildCount() > 0) {
            AppCompatTextView textView = (AppCompatTextView) spinner.getChildAt(spinner.getChildCount() - 1);
            textView.setTextAppearance(getContext(), R.style.textAppearanceRegularBlack14dp);
        }

        spinner = view.findViewById(R.id.spinnercustom_spinner);

        imageView = view.findViewById(R.id.buttondatepicker_icone);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
            }
        });

        addView(view);
    }

    public void setAdapter(ArrayList<HashMap<String, String>> dados, String descricao) {

        SimpleAdapter adapter = new SimpleAdapter(getContext(), dados, R.layout.componente_spinner_custom_text_item_selected, new String[]{descricao}, new int[]{R.id.spinnercustom_textItemSelected});
        adapter.setDropDownViewResource(R.layout.componente_spinner_custom_text_dropdown);
        spinner.setAdapter(adapter);
    }


    public void setSelection(int i) {
        spinner.setSelection(i);
        setEnabled(container.isEnabled());
    }

    public void setSelection(int i, boolean animate) {
        spinner.setSelection(i, animate);
        setEnabled(container.isEnabled());
    }

    public void setPromptId(int promptId) {
        spinner.setPromptId(promptId);
    }

    public Object getSelectedItem() {
        return spinner.getSelectedItem();
    }

    public SpinnerAdapter getAdapter() {
        return spinner.getAdapter();
    }

    public void setAdapter(Object[] arrayFiltro) {
        ArrayAdapter<Object> adapter = new ArrayAdapter<>(getContext(), R.layout.componente_spinner_custom_text_item_selected, arrayFiltro);
        adapter.setDropDownViewResource(R.layout.componente_spinner_custom_text_dropdown);
        spinner.setAdapter(adapter);
    }

    public void setAdapter(String[] arrayFiltro) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.componente_spinner_custom_text_item_selected,
                arrayFiltro);
        adapter.setDropDownViewResource(R.layout.componente_spinner_custom_text_dropdown);
        spinner.setAdapter(adapter);
    }

    public void setAdapter(int arrayFiltro) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), arrayFiltro, R.layout.componente_spinner_custom_text_item_selected); //change the last argument here to your xml above.
        adapter.setDropDownViewResource(R.layout.componente_spinner_custom_text_dropdown);
        spinner.setAdapter(adapter);
    }

    public AdapterView.OnItemSelectedListener getOnItemSelectedListener() {
        return spinner.getOnItemSelectedListener();
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
        spinner.setOnItemSelectedListener(onItemSelectedListener);
    }

    public int getSelectedItemPosition() {
        return spinner.getSelectedItemPosition();
    }

    public int getCount() {
        return spinner.getCount();
    }

    public Object getItemAtPosition(int i) {
        return spinner.getItemAtPosition(i);
    }

    public void eventoClicar() {
        spinner.performClick();
    }
}
