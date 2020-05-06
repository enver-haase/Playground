package org.vaadin.enver.components;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JsModule;
import jdk.internal.jline.internal.Nullable;

import java.time.LocalDate;
import java.util.*;

public class BetterDatePicker extends DatePicker {

    public enum I18N {
        ENGLISH, FRENCH, ITALIAN, GERMAN
    }

    DatePicker.DatePickerI18n german = new DatePicker.DatePickerI18n().setWeek("Woche").setCalendar("Kalender")
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

    DatePickerI18n french = new DatePicker.DatePickerI18n().setWeek("settimana").setCalendar("calendario")
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

    DatePicker.DatePickerI18n italian = new DatePicker.DatePickerI18n().setWeek("settimana").setCalendar("calendario")
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

    DatePicker.DatePickerI18n english = new DatePicker.DatePickerI18n().setWeek("Week").setCalendar("Calendar")
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


    public BetterDatePicker(I18N locale){
        super(LocalDate.now()); // important that it's not NULL

        switch (locale){
            case GERMAN:
                setI18n(german);
                setLocale(Locale.GERMAN);
                setErrorMessage("ungültiges Datum");
                break;
            case FRENCH:
                setI18n(french);
                setLocale(Locale.FRENCH);
                setErrorMessage("date invalide");
                break;
            case ITALIAN:
                setI18n(italian);
                setLocale(Locale.ITALIAN);
                setErrorMessage("data non valida");
                break;
            case ENGLISH:
            default:
                setI18n(english);
                setLocale(Locale.UK); // important - no American day/month swap!
                setErrorMessage("invalid date");
        }

        // clear button is nice
        setClearButtonVisible(true);

        // Select text on getting the focus for easier keyboard input
        addFocusListener( e -> getElement().executeJs("this.$.input.inputElement.select()"));

        // note that only on NULL we do secondary parsing, i.e. trying again on the server side.
        // that's why by default we don't want NULL as a value after creation, we would not get
        // notified on a 'change' from NULL to NULL, even if the string changes.
        addValueChangeListener( e -> {
            if (e.getValue() == null) {
                getElement().executeJs("return this._inputValue").then(
                        json -> setValue(parseSecondary(json.asString()))
                );
            }
        });

        // This is a fall-back for when datepicker is NULL and new input cannot be parsed by
        // client-side. Then secondary parsing is not triggered because a value change NULL -> NULL
        // is not caught. At least do something when the user clicks away.
        addBlurListener( e -> {
            if (isInvalid()) {
                getElement().executeJs("return this._inputValue").then(
                        json -> setValue(parseSecondary(json.asString()))
                );
            }
        });
    }

    /**
     * The parsed date or NULL if it cannot be understood.
     * @param input the text field string
     * @return a date if it can be parsed, or null otherwise
     */
    @Nullable
    private LocalDate parseSecondary(String input){

        try {
            if (input.isEmpty()){
                throw new RuntimeException("unparseable date string");
            }

            // observation: the only delimiters in the four supported date formats are '/' and '.' .
            StringTokenizer st = new StringTokenizer(input, "/.");

            // default for shorter strings
            LocalDate now = LocalDate.now();
            int year = now.getYear();
            int month = now.getMonthValue();
            int dayOfMonth = now.getDayOfMonth();

            if (st.hasMoreTokens()){
                dayOfMonth = Integer.parseInt(st.nextToken());
            }
            if (st.hasMoreTokens()){
                month = Integer.parseInt(st.nextToken());
            }
            if (st.hasMoreTokens()){
                year = Integer.parseInt(st.nextToken());
            }

            return LocalDate.of(year, month, dayOfMonth);
        }
        catch (RuntimeException e){
            setInvalid(true);
            return null;
        }

    }

}
