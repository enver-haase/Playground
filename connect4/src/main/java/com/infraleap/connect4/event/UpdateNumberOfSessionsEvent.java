package com.infraleap.connect4.event;

import com.infraleap.connect4.Connect4Servlet;

import java.util.EventObject;

public class UpdateNumberOfSessionsEvent extends EventObject {

    private int numSessions;

    public UpdateNumberOfSessionsEvent(Connect4Servlet source, int numberOfSessions){
        super(source);
        this.numSessions = numberOfSessions;
    }

    public int getNumberOfSessions(){
        return numSessions;
    }

    @Override
    public Connect4Servlet getSource() {
        return (Connect4Servlet) super.getSource();
    }
}
