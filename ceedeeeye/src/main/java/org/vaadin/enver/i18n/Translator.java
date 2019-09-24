package org.vaadin.enver.i18n;

import com.vaadin.cdi.annotation.VaadinServiceEnabled;
import com.vaadin.flow.i18n.I18NProvider;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.*;


@VaadinServiceEnabled
public class Translator implements I18NProvider {

    private final static Locale DEFAULT_LOCALE = Locale.GERMAN;

    public interface Translation {
        default Optional<ResourceBundle> getTranslation(Locale locale) {
            try {
                return Optional.of(ResourceBundle.getBundle(getBundleBasename(), locale));
            } catch (MissingResourceException mre) {
                return Optional.empty();
            }
        }

        default String getBundleBasename(){
            return "i18n/my-app";
        }

    }

    private final List<Translation> translations = new ArrayList<>();


    @Inject
    Translator(BeanManager beanManager) {

        Set<Bean<?>> translations = beanManager.getBeans(Translation.class);
        for (Bean<?> bean : translations) {
            Bean<Translation> translationBean = (Bean<Translation>) bean;
            CreationalContext<Translation> ctx = beanManager.createCreationalContext(translationBean);
            Translation translation = (Translation) beanManager.getReference(translationBean, Translation.class, ctx);
            this.translations.add(translation);

            System.out.println("############## " + translations.size() + " translations found.");
        }
    }

    @Override
    public List<Locale> getProvidedLocales() {
        return Arrays.asList(Locale.US, Locale.FRANCE, Locale.GERMANY, new Locale("de", "AT"));
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {

        for (Translation tr : translations) {
            Optional<ResourceBundle> rb = tr.getTranslation(locale);
            if (!rb.isPresent()) {
                rb = tr.getTranslation(DEFAULT_LOCALE);
            }

            if (rb.isPresent()) {
                ResourceBundle bundle = rb.get();
                if (bundle.containsKey(key)) {
                    return MessageFormat.format(bundle.getString(key), params);
                }
            }
        }

        return "!!!" + key + "!!!";
    }
}
