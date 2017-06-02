package com.infraleap.connect4.ui;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@Theme("connect4theme")
@SpringUI()
public class Connect4UI extends UI {


    @Autowired
    private HeaderView header;

    @Autowired
    private PlayfieldView playfield;

    @Autowired
    private FooterView footer;


    @Override
    protected void init(VaadinRequest request) {
        // Notice quickly if other UIs are closed
        setPollInterval(3000);

        Layout mainLayout = new VerticalLayout();

        mainLayout.addComponent(header);
        mainLayout.addComponent(playfield);
        mainLayout.addComponent(footer);

        setContent(mainLayout);
    }

    public void pushNumSessions(int numSessions){
        System.out.println(numSessions); // TODO: remove method, use EventBus
    }
}
