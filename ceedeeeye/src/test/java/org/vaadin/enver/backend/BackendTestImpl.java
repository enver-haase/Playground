package org.vaadin.enver.backend;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BackendTestImpl implements Backend {
    @Override
    public String getKingName() {
        return "Enver";
    }
}
