package com.example.application.views.layouting;

import com.vaadin.flow.component.textfield.BigDecimalField;


class MyBigDecimalField extends BigDecimalField {

    MyBigDecimalField(final int descendants, String label){
        super(label);
        setPattern(calcPattern(descendants));
        setPreventInvalidInput(true);
    }

    MyBigDecimalField(final int descendants){
        setPattern(calcPattern(descendants));
        setPreventInvalidInput(true);
    }

    private String calcPattern(final int descendants){
        String pattern = "[0-9]*";
        if (descendants > 0){
            pattern += "(,|.)?";
        }
        pattern+="[0-9]{0,"+descendants+"}";
       return pattern;
    }

}
