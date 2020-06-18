package org.vaadin.enver.viewmodel.field;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@SuppressWarnings("unused")
public class InstantFieldFactory extends FieldFactory<DateTimePicker, Instant> {
    public Component create(String name, boolean editable, boolean mandatory, ValueProvider<Instant> getter, ValueConsumer<Instant> setter){
        LocalDateTime ldt = LocalDateTime.ofInstant(getter.getValue(), ZoneId.of("UTC"));
        DateTimePicker dtp = new DateTimePicker(ldt);
        dtp.setLabel(name);
        dtp.setReadOnly(!editable);
        dtp.setRequiredIndicatorVisible(mandatory);
        dtp.addValueChangeListener( event -> setter.setValue(event.getValue().toInstant(ZoneOffset.UTC)) );
        return new DateTimePicker(ldt);
    }
}
