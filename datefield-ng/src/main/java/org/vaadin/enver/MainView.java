package org.vaadin.enver;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.enver.components.BetterDatePicker;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@SuppressWarnings("unused")
public class MainView extends VerticalLayout {

    @SuppressWarnings("unused")
    public MainView() {

        BetterDatePicker betterDatePicker = new BetterDatePicker();
        Text message = new Text(betterDatePicker.getValue() == null ? "(null)" : betterDatePicker.getValue().toString());

        betterDatePicker.addValueChangeListener( v -> message.setText(v == null ? "(null)" : v.toString()) );

        add(betterDatePicker, message);
    }
}
