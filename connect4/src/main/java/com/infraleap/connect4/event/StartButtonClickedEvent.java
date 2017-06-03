package com.infraleap.connect4.event;

import com.infraleap.connect4.ui.Connect4UI;

import java.util.EventObject;

public class StartButtonClickedEvent extends EventObject {

    public StartButtonClickedEvent(Connect4UI source) {
        super(source);
    }

    @Override
    public Connect4UI getSource() {
        return (Connect4UI) super.getSource();
    }
}
