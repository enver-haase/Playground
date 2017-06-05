package com.infraleap.connect4.event;

import com.infraleap.connect4.Connect4SessionState;

import java.util.EventObject;

public class StateChangeEvent extends EventObject {

    public StateChangeEvent(Connect4SessionState source){
        super(source);
    }

    @Override
    public Connect4SessionState getSource() {
        return (Connect4SessionState) super.getSource();
    }
}
