package com.infraleap.connect4.ui;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.infraleap.connect4.Connect4Servlet;
import com.infraleap.connect4.event.StartButtonClickedEvent;
import com.infraleap.connect4.event.UpdateNumberOfSessionsEvent;
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
        // init() is called after attach, so we are attached!
        getEventBus().register(this);

        // Notice quickly if other UIs are closed
        setPollInterval(3000);

        Layout mainLayout = new VerticalLayout();

        mainLayout.addComponent(header);
        header.startButton.addClickListener(event -> ((Connect4UI) getUI()).getEventBus().post(new StartButtonClickedEvent(this)));
        header.numberUsersLabel.setValue(String.valueOf(getCurrenfNumberOfSessions()));

        mainLayout.addComponent(playfield);
        mainLayout.addComponent(footer);

        setContent(mainLayout);
    }

    @Override
    public void detach(){
        // attach is called before init()
        getEventBus().unregister(this);
        super.detach();
    }

    EventBus getEventBus(){
        return ((Connect4Servlet) (getSession().getAttribute(Connect4Servlet.SESSION_KEY))).getConnect4EventBus();
    }

    private int getCurrenfNumberOfSessions(){
        return ((Connect4Servlet) (getSession().getAttribute(Connect4Servlet.SESSION_KEY))).getCurrentNumberOfSessions();
    }

    @Subscribe
    public void handleUpdateNumberOfSessions(UpdateNumberOfSessionsEvent e){
        header.numberUsersLabel.setValue(String.valueOf(e.getNumberOfSessions()));
    }

}
