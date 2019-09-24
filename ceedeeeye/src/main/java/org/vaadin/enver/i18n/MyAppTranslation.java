package org.vaadin.enver.i18n;

import com.vaadin.cdi.annotation.VaadinServiceScoped;

import java.util.*;

@VaadinServiceScoped
public class MyAppTranslation implements Translator.Translation {

    private final HashMap<Locale, ResourceBundle> cache = new HashMap<>();

    @Override
    public Optional<ResourceBundle> getTranslation(Locale locale) {
        try {
            return Optional.of(cache.getOrDefault(locale, cache.put(locale, ResourceBundle.getBundle("i18n/my-app", locale))));
        }
        catch (MissingResourceException mre){
            return Optional.empty();
        }
    }
}
