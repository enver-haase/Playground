package com.infraleap.connect4.ui;

import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;

@SpringComponent
@UIScope
public class HeaderView extends HeaderDesign {

    public HeaderView(){
        this.startButton.addClickListener(event -> VaadinSession.getCurrent().close());
    }

}
