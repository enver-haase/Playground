package com.infraleap.connect4.event;

import com.infraleap.connect4.Connect4SessionState;
import com.infraleap.connect4.ui.Connect4UI;

import java.util.EventObject;

public class MoveMadeEvent extends EventObject {

    private int column;

    public MoveMadeEvent(Connect4SessionState source, int column){
        super(source);
        this.column = column;
    }

    public int getMove(){
        return column;
    }

    @Override
    public Connect4SessionState getSource() {
        return (Connect4SessionState) super.getSource();
    }
}
