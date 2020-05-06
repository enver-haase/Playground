package org.vaadin.enver.components;

import com.vaadin.flow.component.datepicker.DatePicker;

import java.util.Locale;

public class BetterDatePicker extends DatePicker {
    public BetterDatePicker(){
        super();

        setLocale(Locale.GERMANY);

        // Select text on getting the focus for easier keyboard input
        addFocusListener( e -> getElement().executeJs("this.$.input.inputElement.select()"));
    }

    @Override
    public void setLocale(Locale ignored) {
        super.setLocale(Locale.GERMANY); //Don't set a locale here, it could destroy correctness of our special connector implementation.
    }
}
