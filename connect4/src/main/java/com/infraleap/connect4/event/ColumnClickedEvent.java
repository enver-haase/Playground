package com.infraleap.connect4.event;


import com.infraleap.connect4.ui.Connect4UI;

import java.util.EventObject;

public class ColumnClickedEvent extends EventObject {

    private int column;

    public ColumnClickedEvent(Connect4UI source, int column){
        super(source);
        this.column = column;
    }

    public int getColumn(){
        return column;
    }

    @Override
    public Connect4UI getSource() {
        return (Connect4UI) super.getSource();
    }
}
