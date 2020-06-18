package org.vaadin.enver.viewmodel.field;

import com.vaadin.flow.component.Component;

public abstract class FieldFactory<C extends Component, T> {
    public abstract Component create(String name, boolean editable, boolean mandatory, ValueProvider<T> getter, ValueConsumer<T> setter);
}
