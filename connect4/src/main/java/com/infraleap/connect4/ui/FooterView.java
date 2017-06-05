package com.infraleap.connect4.ui;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

import java.net.URI;

@SpringComponent
@UIScope
public class FooterView extends FooterDesign{
    public FooterView(){
        startOverButton.addClickListener( event -> {
            URI page = getUI().getPage().getLocation();
            VaadinSession session = getSession();
            session.getSession().invalidate();
            session.close();
            Page.getCurrent().setLocation(page);
        } );
    }
}
