package com.infraleap.connect4.event;

import com.infraleap.connect4.ui.Connect4UI;

import java.util.EventObject;

public class PlayernameChangedEvent extends EventObject {

    private String playername;

    public PlayernameChangedEvent(Connect4UI source, String playername){
        super(source);
        this.playername = playername;
    }

    public String getPlayername(){
        return playername;
    }

    @Override
    public Connect4UI getSource() {
        return (Connect4UI) super.getSource();
    }
}
