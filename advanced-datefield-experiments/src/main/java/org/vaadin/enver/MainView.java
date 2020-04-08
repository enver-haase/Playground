package org.vaadin.enver;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.vaadin.enver.components.BetterDatePicker;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Locale;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@PWA(name = "Project Base for Vaadin", shortName = "Project Base")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {


    // Customer supports German, English, Italian and French
    public MainView() {

        DatePicker.DatePickerI18n germani18n = new DatePicker.DatePickerI18n().setWeek("Woche").setCalendar("Kalender")
                .setClear("löschen").setToday("heute")
                .setCancel("abbrechen").setFirstDayOfWeek(1)
                .setMonthNames(Arrays.asList("Januar", "Februar",
                        "März", "April", "Mai", "Juni",
                        "Juli", "August", "September", "Oktober",
                        "November", "Dezember")).setWeekdays(
                        Arrays.asList("Sonntag", "Montag", "Dienstag",
                                "Mittwoch", "Donnerstag", "Freitag",
                                "Samstag")).setWeekdaysShort(
                        Arrays.asList("So", "Mo", "Di", "Mi", "Do", "Fr",
                                "Sa"));
        Component german = create(germani18n, Locale.GERMAN);


        DatePicker.DatePickerI18n englishI18n = new DatePicker.DatePickerI18n().setWeek("Week").setCalendar("Calendar")
                .setClear("clear").setToday("today")
                .setCancel("cancel").setFirstDayOfWeek(1)
                .setMonthNames(Arrays.asList("January", "February",
                        "March", "April", "May", "June",
                        "July", "August", "September", "October",
                        "November", "December")).setWeekdays(
                        Arrays.asList("Sunday", "Monday", "Tuesday",
                                "Wednesday", "Thursday", "Friday",
                                "Saturday")).setWeekdaysShort(
                        Arrays.asList("Sun", "Mon", "Tue", "Wed", "Thu", "Fri",
                                "Sat"));
        Component english = create(englishI18n, Locale.UK);


        DatePicker.DatePickerI18n italian18n = new DatePicker.DatePickerI18n().setWeek("settimana").setCalendar("calendario")
                .setClear("cancellare").setToday("oggi")
                .setCancel("annullare").setFirstDayOfWeek(1)
                .setMonthNames(Arrays.asList("Gennaio", "Febbrario",
                        "Marzo", "Aprile", "Maggio", "Giugno",
                        "Luglio", "Agosto", "Settembre", "Ottobre",
                        "Novembre", "Dicembre")).setWeekdays(
                        Arrays.asList("Domenica", "Lunedì", "Martedì",
                                "Mercoledì", "Giovedì", "Venerdì",
                                "Sabato")).setWeekdaysShort(
                        Arrays.asList("Do", "Lu", "Ma", "Me", "Gi", "Ve",
                                "Sa"));
        Component italian = create(italian18n, Locale.ITALIAN);




        DatePicker.DatePickerI18n french18n = new DatePicker.DatePickerI18n().setWeek("settimana").setCalendar("calendario")
                .setClear("effacer").setToday("aujourd'hui")
                .setCancel("annuller").setFirstDayOfWeek(1)
                .setMonthNames(Arrays.asList("Janvier", "Février",
                        "Mars", "Avril", "Mai", "Juin",
                        "Juillet", "Août", "Septembre", "Octobre",
                        "Novembre", "Décembre")).setWeekdays(
                        Arrays.asList("Dimanche", "Lundi", "Mardi",
                                "Mercredi", "Jeudi", "Vendredi",
                                "Samedi")).setWeekdaysShort(
                        Arrays.asList("Di", "Lu", "Ma", "Me", "Je", "Ve",
                                "Sa"));
        Component french = create(french18n, Locale.FRENCH);



        add(german, english, italian, french);
    }


    private Component create(DatePicker.DatePickerI18n i18n, Locale locale) {

        VerticalLayout vl = new VerticalLayout();

        Text message = new Text("");

        BetterDatePicker betterDatePicker = new BetterDatePicker();
        if (i18n != null) {
            betterDatePicker.setI18n(i18n);
        }
        betterDatePicker.setLocale(locale);
        betterDatePicker.setClearButtonVisible(true);

        betterDatePicker.addValueChangeListener(event -> {
            LocalDate selectedDate = event.getValue();
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
        });


        betterDatePicker.addFocusListener( e -> {
           betterDatePicker.getElement().executeJs("document.getElementById(\"input\").select();");
        });


        vl.add(betterDatePicker, message);

        return vl;
    }
}
