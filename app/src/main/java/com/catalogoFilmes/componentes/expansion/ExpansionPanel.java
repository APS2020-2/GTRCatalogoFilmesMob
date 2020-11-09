package com.catalogoFilmes.componentes.expansion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;

import com.aps2020.CatalogoFilmes.R;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ExpansionPanel extends LinearLayout {

    private View header;
    private View layout;
    private ExpansionLayout expLayout;

    public ExpansionPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ExpansionPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.layout_expansionpanel, this, false);

        @SuppressLint("Recycle") TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ExpansionPanel, 0, 0);
        int expansionHeader = typedArray.getResourceId(R.styleable.ExpansionPanel_expansionHeader, 0);
        int expansionLayout = typedArray.getResourceId(R.styleable.ExpansionPanel_expansionLayout, 0);

        header = view.findViewById(R.id.expansionpanel_header);
        expLayout = view.findViewById(R.id.expansionpanel_expansion);

        ViewStub viewStubHeader = view.findViewById(R.id.expansionpanel_viewStubHeader);
        viewStubHeader.setLayoutResource(expansionHeader);
        header = viewStubHeader.inflate();

        ViewStub viewStubLayout = view.findViewById(R.id.expansionpanel_viewStubLayout);
        viewStubLayout.setLayoutResource(expansionLayout);
        layout = viewStubLayout.inflate();

        addView(view);
    }

    public ExpansionLayout getExpLayout() {
        return expLayout;
    }
}
