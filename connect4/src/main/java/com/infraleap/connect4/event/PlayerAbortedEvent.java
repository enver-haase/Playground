package com.infraleap.connect4.event;

import com.infraleap.connect4.Connect4Servlet;

import java.util.EventObject;

public class PlayerAbortedEvent extends EventObject {

    private PlayerData player;

    public PlayerAbortedEvent(Connect4Servlet source, PlayerData player){
        super(source);
        this.player = player;
    }

    public PlayerData getPlayer(){
        return player;
    }

    @Override
    public Connect4Servlet getSource() {
        return (Connect4Servlet) super.getSource();
    }

}
