package com.infraleap.connect4.event;


import com.infraleap.connect4.Connect4Servlet;
import com.infraleap.connect4.Connect4SessionState;

import java.util.EventObject;

public class GameStartEvent extends EventObject {

    private Connect4SessionState firestPlayer;
    private Connect4SessionState secondPlayer;

    public GameStartEvent(Connect4Servlet source, Connect4SessionState firstPlayer, Connect4SessionState secondPlayer){
        super(source);
        this.firestPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public Connect4SessionState getFirstPlayer(){
        return firestPlayer;
    }

    public Connect4SessionState getSecondPlayer(){
        return secondPlayer;
    }

    @Override
    public Connect4Servlet getSource() {
        return (Connect4Servlet) super.getSource();
    }
}
