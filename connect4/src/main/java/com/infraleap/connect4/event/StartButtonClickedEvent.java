package com.infraleap.connect4.event;

import com.infraleap.connect4.Connect4SessionState;
import com.infraleap.connect4.ui.Connect4UI;

import java.util.EventObject;

public class StartButtonClickedEvent extends EventObject {

    Connect4SessionState requestor;

    public StartButtonClickedEvent(Connect4UI source, Connect4SessionState requestor) {
        super(source);
        this.requestor = requestor;
    }

    @Override
    public Connect4UI getSource() {
        return (Connect4UI) super.getSource();
    }

    public Connect4SessionState getRequestor() { return requestor; }
}
