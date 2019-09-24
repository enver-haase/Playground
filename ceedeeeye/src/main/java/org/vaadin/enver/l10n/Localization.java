package org.vaadin.enver.l10n;

import com.vaadin.cdi.annotation.VaadinServiceScoped;
import org.vaadin.enver.i18n.Translator;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

@VaadinServiceScoped
public class Localization implements Translator.Translation {

    @Override
    public Optional<ResourceBundle> getTranslation(Locale locale) {
        try {
            return Optional.of(ResourceBundle.getBundle("l10n/loca", locale));
        }
        catch (MissingResourceException mre){
            return Optional.empty();
        }
    }
}
