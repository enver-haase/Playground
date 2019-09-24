package org.vaadin.enver.backend;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BackendImpl implements Backend{

    @Override
    public String getKingName(){
        return "Real Backend Implementation";
    }
}
