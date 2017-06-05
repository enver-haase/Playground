package com.infraleap.connect4.event;


import com.infraleap.connect4.Connect4Servlet;
import java.util.EventObject;

public class GameStartEvent extends EventObject {

    private PlayerData firstPlayer;
    private PlayerData secondPlayer;

    public GameStartEvent(Connect4Servlet source, PlayerData firstPlayer, PlayerData secondPlayer){
        super(source);
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public PlayerData getFirstPlayer(){
        return firstPlayer;
    }

    public PlayerData getSecondPlayer(){
        return secondPlayer;
    }

    @Override
    public Connect4Servlet getSource() {
        return (Connect4Servlet) super.getSource();
    }
}
