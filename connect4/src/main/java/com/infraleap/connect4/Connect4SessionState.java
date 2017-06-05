package com.infraleap.connect4;

import com.google.common.eventbus.Subscribe;
import com.infraleap.connect4.event.GameStartEvent;
import com.infraleap.connect4.event.MoveMadeEvent;
import com.infraleap.connect4.event.StateChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import org.springframework.web.context.annotation.SessionScope;

@VaadinSessionScope
@SpringComponent
public class Connect4SessionState {

    public enum Coin {
        RED,
        YELLOW,
        EMPTY
    };

    private String playerName;
    private Coin[][] playfield;
    private boolean contestantRequested;

    private Connect4SessionState contestant;

    /**
     * Coin.EMPTY: game is not in progress. Otherwise, our color.
     */
    private Coin myColor;

    /**
     * Coin.EMPTY: game is not in progress. Otherwise, who is up right now.
     */
    private Coin playerUp;

    public Connect4SessionState(Connect4Servlet servlet){

        Connect4Servlet.theEventBus.register(this);

        this.playerName = "";

        this.playfield = new Coin[7][6];
        for (int col = 0; col < playfield.length; col++){
            for (int row = 0; row < playfield[col].length; row++)
            {
                playfield[col][row] = Coin.EMPTY;
            }
        }

        this.myColor = Coin.EMPTY; // game is not in progress
        this.playerUp = Coin.EMPTY; //
        this.contestantRequested = false;

        this.contestant = null;
    }


    public void setPlayername(String name){
        if (!name.equals(this.playerName)) {
            this.playerName = name;
            postUpdateEvent();
        }
    }
    public String getPlayerName(){
        return playerName;
    }

    public String getContestantName() { return contestant==null? "" : contestant.playerName; }

    public void setPlayfield(Coin[][] playfield){
        this.playfield = playfield;
    }
    public Coin[][] getPlayfield(){
        return playfield;
    }

    public Coin getPlayerUp(){
        return playerUp;
    }

    public Coin getColor(){
        return myColor;
    }

    public boolean contestantRequested(){
        return contestantRequested;
    }

    public void requestContestant(){
        this.contestantRequested = true;
        postUpdateEvent();
    }

    private void postUpdateEvent(){
        Connect4Servlet.theEventBus.post(new StateChangeEvent(this));
    }

    @Subscribe
    public void handleGameStart(GameStartEvent event){
        this.playerUp = Coin.YELLOW; // yellow always starts

        if (event.getFirstPlayer() == this){
            this.myColor = Coin.YELLOW;

        }
        else if (event.getSecondPlayer() == this){
            this.myColor = Coin.RED;
        }
    }

    @Subscribe
    public void handleMoveMade(MoveMadeEvent event){
        System.out.println("MOVE MADE - SESSION STATE SAYS.");
    }

}
