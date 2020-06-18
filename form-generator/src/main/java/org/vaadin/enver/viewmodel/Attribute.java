package org.vaadin.enver.viewmodel;

import com.vaadin.flow.component.Component;
import jdk.internal.joptsimple.internal.Strings;
import lombok.SneakyThrows;
import org.vaadin.enver.viewmodel.field.FieldFactory;
import org.vaadin.enver.viewmodel.field.ValueConsumer;
import org.vaadin.enver.viewmodel.field.ValueProvider;

import java.util.Optional;

public final class Attribute<T>  {

    private final String name;
    private final boolean editable;
    private final boolean mandatory;
    private final T value;
    private final ValueConsumer<T> setter;
    private final ValueProvider<T> getter;

    public Attribute(String name, T value, boolean editable, boolean mandatory, ValueProvider<T> getter, ValueConsumer<T> setter){
        this.name = name;
        this.value = value;
        this.editable = editable;
        this.mandatory = mandatory;
        this.getter = getter;
        this.setter = setter;

        assert( !Strings.isNullOrEmpty(name) && (!mandatory || value!=null) );
    }

    public final String getName(){
        return name;
    }

    public final boolean isEditable(){
        return editable;
    }

    public final boolean isMandatory(){
        return mandatory;
    }

    public final boolean isOptional(){
        return !isMandatory();
    }

    public final Optional<T> getValue(){
        return ( value == null? Optional.empty() : Optional.of(value) );
    }

    @SneakyThrows
    public Component createField(){
        String typeName = value.getClass().getTypeName();
        typeName = typeName.substring(typeName.lastIndexOf(".")+1);
        typeName = "org.vaadin.enver.viewmodel.field."+typeName;

        @SuppressWarnings("unchecked")
        FieldFactory<Component, T> factory = (FieldFactory<Component, T>) Class.forName(typeName+"FieldFactory").getConstructor().newInstance();
        return factory.create(this.name, this.editable, this.mandatory, getter, setter);
    }
}
