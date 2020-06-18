package org.vaadin.enver.viewmodel.field;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextField;

@SuppressWarnings("unused")
public class StringFieldFactory extends FieldFactory<TextField, String> {
    public Component create(String name, boolean editable, boolean mandatory, ValueProvider<String> getter, ValueConsumer<String> setter) {
        TextField tf = new TextField(name);
        tf.setValue(getter.getValue());
        tf.setEnabled(editable);
        tf.setRequired(mandatory);
        tf.setRequiredIndicatorVisible(mandatory);
        tf.addValueChangeListener( event -> setter.setValue(event.getValue()));
        return tf;
    }
}
