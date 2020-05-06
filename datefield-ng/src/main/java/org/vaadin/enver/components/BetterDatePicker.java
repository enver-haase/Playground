package org.vaadin.enver.components;

import com.vaadin.flow.component.datepicker.DatePicker;

public class BetterDatePicker extends DatePicker {
    public BetterDatePicker(){
        super();

        // Select text on getting the focus for easier keyboard input
        addFocusListener( e -> getElement().executeJs("this.$.input.inputElement.select()"));
    }
}
