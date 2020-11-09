package com.catalogoFilmes.componentes.expansion;

import java.util.Collection;
import java.util.HashSet;

public class ExpansionLayoutCollection {

    private final Collection<ExpansionLayout> expansions = new HashSet<>();
    private boolean openOnlyOne = true;

    private final ExpansionLayout.IndicatorListener indicatorListener = new ExpansionLayout.IndicatorListener() {
        @Override
        public void onStartedExpand(ExpansionLayout expansionLayout, boolean willExpand) {
            if (willExpand && openOnlyOne) {
                for (ExpansionLayout view : expansions) {
                    if (view != expansionLayout) {
                        view.collapse(true);
                    }
                }
            }
        }
    };

    public void add(ExpansionLayout expansionLayout) {
        expansions.add(expansionLayout);
        expansionLayout.addIndicatorListener(indicatorListener);
    }

    public void openOnlyOne(boolean openOnlyOne) {
        this.openOnlyOne = openOnlyOne;
    }
}
