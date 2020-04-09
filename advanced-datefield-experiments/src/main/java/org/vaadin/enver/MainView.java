package org.vaadin.enver;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.enver.components.BetterDatePicker;

import java.time.LocalDate;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@SuppressWarnings("unused")
public class MainView extends VerticalLayout {

    // Customer supports German, English, Italian and French
    @SuppressWarnings("unused")
    public MainView() {
        Component german = create(BetterDatePicker.I18N.GERMAN);
        Component english = create(BetterDatePicker.I18N.ENGLISH);
        Component italian = create(BetterDatePicker.I18N.ITALIAN);
        Component french = create(BetterDatePicker.I18N.FRENCH);

        add(german, english, italian, french);
    }


    private Component create(BetterDatePicker.I18N i18n) {

        VerticalLayout vl = new VerticalLayout();

        Text message = new Text("");

        BetterDatePicker betterDatePicker = new BetterDatePicker(i18n);

        betterDatePicker.addValueChangeListener(event -> {
            LocalDate selectedDate = event.getValue();
            showDateResults(message, betterDatePicker, selectedDate);
        });

        // initially show something
        showDateResults(message, betterDatePicker, betterDatePicker.getValue());

        // application hack to make our listener fire
        betterDatePicker.setValue(LocalDate.MIN);
        betterDatePicker.setValue(LocalDate.now());

        vl.add(betterDatePicker, message);

        return vl;
    }

    private void showDateResults(Text message, BetterDatePicker betterDatePicker, LocalDate selectedDate) {
        if (selectedDate != null) {
            int weekday = selectedDate.getDayOfWeek().getValue() % 7;
            String weekdayName = betterDatePicker.getI18n().getWeekdays()
                    .get(weekday);

            int month = selectedDate.getMonthValue() - 1;
            String monthName = betterDatePicker.getI18n().getMonthNames()
                    .get(month);

            message.setText("Day of week: " + weekdayName + "\nMonth: "
                    + monthName);
        } else {
            message.setText("No date is selected");
        }
    }


}
