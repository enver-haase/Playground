package com.infraleap.connect4.event;

import com.infraleap.connect4.ui.Connect4UI;
import com.vaadin.ui.UI;

import java.util.EventObject;

public class UsernameChangedEvent extends EventObject {

    private String username;

    public UsernameChangedEvent(Connect4UI source, String username){
        super(source);
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    @Override
    public UI getSource() {
        return (Connect4UI) super.getSource();
    }
}
