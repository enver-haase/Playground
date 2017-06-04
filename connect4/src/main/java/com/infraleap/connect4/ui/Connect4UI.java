package com.infraleap.connect4.ui;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.infraleap.connect4.Connect4Servlet;
import com.infraleap.connect4.Connect4SessionState;
import com.infraleap.connect4.event.ContestantRequestAcceptedEvent;
import com.infraleap.connect4.event.StartButtonClickedEvent;
import com.infraleap.connect4.event.UpdateNumberOfSessionsEvent;
import com.infraleap.connect4.event.PlayernameChangedEvent;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import static com.infraleap.connect4.Connect4Servlet.SESSION_KEY_SERVLET;

@Theme("connect4theme")
@SpringUI()
public class Connect4UI extends UI {

    @Autowired
    private Connect4SessionState state;

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

        header.playerName.setValue(state.getPlayerName());
        header.playerName.addValueChangeListener(event -> { state.setPlayerName(event.getValue());
                                                            getEventBus().post(new PlayernameChangedEvent(this, event.getValue())); });

        header.contestantName.setReadOnly(true);

        header.startButton.addClickListener(event -> getEventBus().post(new StartButtonClickedEvent(this)));
        header.numberUsersLabel.setValue(String.valueOf(getCurrentNumberOfSessions()));

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

    private EventBus getEventBus(){
        return ((Connect4Servlet) (getSession().getAttribute(SESSION_KEY_SERVLET))).getConnect4EventBus();
    }

    private int getCurrentNumberOfSessions(){
        return ((Connect4Servlet) (getSession().getAttribute(SESSION_KEY_SERVLET))).getCurrentNumberOfSessions();
    }

    @Subscribe
    public void handleUpdateNumberOfSessions(UpdateNumberOfSessionsEvent e){
        header.numberUsersLabel.setValue(String.valueOf(e.getNumberOfSessions()));
    }

    @Subscribe
    public void handlePlayerNameChanged(PlayernameChangedEvent e){
//        if (e.getSource().getSession() == getSession()) {
//            // make sure we ignore other sessions. We could as well check our session state and update from there.
//            setPlayerName(e.getPlayername());
//        }
        setPlayerName(state.getPlayerName());
    }

    @Subscribe
    public void handleContestantRequest(ContestantRequestAcceptedEvent event){
        // TODO
    }


    private void setContestantName(String name){
        header.contestantName.setReadOnly(false);
        header.contestantName.setValue(name);
        header.contestantName.setReadOnly(true);
    }

    private void setPlayerName(String name)
    {
        header.playerName.setValue(name);
    }
}
