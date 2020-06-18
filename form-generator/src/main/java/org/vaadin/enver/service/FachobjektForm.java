package org.vaadin.enver.service;

import com.vaadin.flow.component.formlayout.FormLayout;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.vaadin.enver.service.backend.DTO;
import org.vaadin.enver.viewmodel.Attribute;
import org.vaadin.enver.viewmodel.field.ValueConsumer;
import org.vaadin.enver.viewmodel.field.ValueProvider;

import java.beans.PropertyDescriptor;
import java.util.*;

@Slf4j
public final class FachobjektForm<T extends Class<DTO>> extends FormLayout {

    private final Map<String, Attribute> map;

    private static final String PROP_SUFFIX_EDITABLE = "Editable";
    private static final String PROP_SUFFIX_MANDATORY = "Mandatory";
    private static final String PROP_SUFFIX_PERMISSION = "Permission";

    @SneakyThrows
    public FachobjektForm(DTO dto){

        this.map = new HashMap<>();

        PropertyDescriptor[] propDescs = PropertyUtils.getPropertyDescriptors(dto);
        for (PropertyDescriptor propertyDescriptor : propDescs){
            String propName = propertyDescriptor.getName();
            if (propName.endsWith(PROP_SUFFIX_EDITABLE) || propName.endsWith(PROP_SUFFIX_MANDATORY) || propName.endsWith(PROP_SUFFIX_PERMISSION) ){
                continue; // skip over those properties that merely are attributes of the main value in question
            }

            Object value = PropertyUtils.getProperty(dto, propName);
            boolean editable = (Boolean) PropertyUtils.getProperty(dto, propName+PROP_SUFFIX_EDITABLE);
            boolean mandatory = (Boolean) PropertyUtils.getProperty(dto, propName+PROP_SUFFIX_MANDATORY);
            boolean permission = (Boolean) PropertyUtils.getProperty(dto, propName+PROP_SUFFIX_PERMISSION);
            if (!permission){
                continue;
            }

            ValueProvider getter = ( () -> {
                try {
                    return PropertyUtils.getProperty(dto, propName);
                }
                catch(Exception ignored){
                    log.error("Cannot get property:", ignored);
                    return null;
                }
            } );
            ValueConsumer setter = ( val -> {
                try {
                    PropertyUtils.setProperty(dto, propName, val);
                } catch (Exception ignored) {
                    log.error("Cannot set property:", ignored);
                }
            } );

            this.map.put(propName, new Attribute(propName, value, editable, mandatory, getter, setter));
        }

        for (Attribute attribute : map.values()){
            add(attribute.createField());
        }
    }




    public Attribute getAttribute(String attributeName) {
        return map.get(attributeName);
    }

    public Collection<String> getAttributeNames() {
        return Collections.unmodifiableCollection(map.keySet());
    }
}
