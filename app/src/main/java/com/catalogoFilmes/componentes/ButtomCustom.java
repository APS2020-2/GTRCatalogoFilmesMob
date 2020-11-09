package com.catalogoFilmes.componentes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.aps2020.CatalogoFilmes.R;

public class ButtomCustom extends ConstraintLayout {

    private AppCompatTextView textButton;

    public ButtomCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ButtomCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ButtomCustom, 0, 0);
        Drawable imageDrawable = typedArray.getDrawable(R.styleable.ButtomCustom_srcButton);
        Drawable backgroundDrawable = typedArray.getDrawable(R.styleable.ButtomCustom_backgroundButton);
        String text = typedArray.getString(R.styleable.ButtomCustom_textButton);
        String textAppearence = typedArray.getString(R.styleable.ButtomCustom_textAppearanceButton);
        float paddingHorizontal = typedArray.getDimension(R.styleable.ButtomCustom_paddingHorizontalButton, 0);
        typedArray.recycle();

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view;
        view = inflater.inflate(R.layout.component_button_custom, this, false);
        ConstraintLayout rootView = view.findViewById(R.id.buttoncustom_rootView);


        textButton = view.findViewById(R.id.buttoncustom_text);
        AppCompatImageView image = view.findViewById(R.id.buttoncustom_imageStart);

        if (imageDrawable != null) {
            image.setVisibility(VISIBLE);
            image.setImageDrawable(imageDrawable);
            textButton.setPadding((int) getContext().getResources().getDimension(R.dimen.space3), 0, 0, 0);
        }

        if (!"".equals(text))
            textButton.setText(text);

        if (textAppearence != null && !"".equals(textAppearence))
            textButton.setTextAppearance(getContext(), Integer.parseInt(textAppearence.replace("@", "")));

        if (backgroundDrawable != null)
            rootView.setBackground(backgroundDrawable);

        if (paddingHorizontal != 0f)
            rootView.setPadding(
                    (int) paddingHorizontal,
                    (int) getResources().getDimension(R.dimen.space3),
                    (int) paddingHorizontal,
                    (int) getResources().getDimension(R.dimen.space3));

        addView(view);
    }

    public void setTextSize(float valor) {
        textButton.setTextSize(valor);
    }

    public void setText(String valor) {
        textButton.setText(valor);
    }
}
