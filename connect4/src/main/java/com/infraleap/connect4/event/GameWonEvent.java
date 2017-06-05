package com.infraleap.connect4.event;

import com.infraleap.connect4.Connect4SessionState;

import java.util.EventObject;

public class GameWonEvent extends EventObject {

    private String reason;

    /**
     * The source is the winner.
     *
     * @param source
     * @param reason
     */
    public GameWonEvent(Connect4SessionState source, String reason){
        super(source);
        this.reason = reason;
    }

    public String getReason(){
        return reason;
    }

    @Override
    public Connect4SessionState getSource() {
        return (Connect4SessionState) super.getSource();
    }
}
