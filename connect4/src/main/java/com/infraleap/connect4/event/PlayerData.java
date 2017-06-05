package com.infraleap.connect4.event;

import com.infraleap.connect4.Connect4SessionState;
import com.vaadin.server.VaadinSession;

public class PlayerData {

    private Connect4SessionState state;
    private VaadinSession session;

    public PlayerData(Connect4SessionState state, VaadinSession session) {
        this.state = state;
        this.session = session;
    }

    public Connect4SessionState getPlayerState(){
        return state;
    }

    public VaadinSession getSession(){
        return this.session;
    }
}
