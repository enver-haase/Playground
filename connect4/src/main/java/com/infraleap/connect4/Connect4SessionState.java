package com.infraleap.connect4;

import com.google.common.eventbus.Subscribe;
import com.infraleap.connect4.event.*;
import com.infraleap.connect4.ui.PlayfieldView;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.Image;

@VaadinSessionScope
@SpringComponent
public class Connect4SessionState {

    private final int ROWS = 6;
    private final int COLUMNS = 7;

    public enum Coin {
        RED,
        YELLOW,
        EMPTY
    };

    private String playerName;
    private Coin[][] playfield;
    private boolean contestantRequested;
    private boolean gameWon;
    private boolean gameLost;
    private String gameEndReason;

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

        this.playfield = new Coin[COLUMNS][ROWS];
        for (int col = 0; col < playfield.length; col++){
            for (int row = 0; row < playfield[col].length; row++)
            {
                playfield[col][row] = Coin.EMPTY;
            }
        }

        this.myColor = Coin.EMPTY; // game is not in progress
        this.playerUp = Coin.EMPTY; //
        this.contestantRequested = false;
        this.gameLost = false;
        this.gameWon = false;
        this.gameEndReason = null;

        this.contestant = null;
    }


    public void setPlayername(String name){
        if (!name.equals(this.playerName)) {
            this.playerName = name;
            postStateChangeEvent();
        }
    }
    public String getPlayerName(){
        return playerName;
    }

    public String getContestantName() { return contestant==null? "" : contestant.playerName; }

    public Coin[][] getPlayfield(){
        return playfield;
    }

    public Coin getPlayerUp(){
        return playerUp;
    }

    public Coin getColor(){
        return myColor;
    }

    public boolean getGameWon() { return gameWon; }

    public boolean getGameLost() { return gameLost; }

    public String getGameEndReason(){
        return gameEndReason;
    }

    public boolean contestantRequested(){
        return contestantRequested;
    }

    public void requestContestant(VaadinSession session){
        this.contestantRequested = true;
        postStateChangeEvent();
        Connect4Servlet.theEventBus.post(new ContestantRequestEvent(new PlayerData(this, session)));
    }

    private void postStateChangeEvent(){
        Connect4Servlet.theEventBus.post(new StateChangeEvent(this));
    }

    @Subscribe
    public void handleGameStart(GameStartEvent event){
        this.playerUp = Coin.YELLOW; // yellow always starts

        if (event.getFirstPlayer().getPlayerState() == this){
            this.myColor = Coin.YELLOW;
            contestant = event.getSecondPlayer().getPlayerState();
        }
        else if (event.getSecondPlayer().getPlayerState() == this){
            this.myColor = Coin.RED;
            contestant = event.getFirstPlayer().getPlayerState();
        }

        postStateChangeEvent();
    }

    @Subscribe
    public void handleMoveMade(MoveMadeEvent event){
        if (event.getSource() == this){
            int column = event.getMove();
            for (int row = ROWS-1; row >= 0; row--){
                if (playfield[column][row] == Coin.EMPTY){

                    playfield[column][row] = playerUp;
                    contestant.playfield[column][row] = playerUp;

                    playerUp = (playerUp == Coin.RED? Coin.YELLOW : Coin.RED); // swap players
                    contestant.playerUp = playerUp;

                    postStateChangeEvent();
                    contestant.postStateChangeEvent();

                    break;
                }
            }
        }
    }

    @Subscribe
    public void handleSessionClosed(PlayerAbortedEvent event){
        if (event.getPlayer().getPlayerState() == contestant){
            Connect4Servlet.theEventBus.post(new GameWonEvent(this, "Other player went away."));
        }
    }

    @Subscribe
    public void handleWon(GameWonEvent event){
        if (event.getSource() == this){
            playerUp = Coin.EMPTY;
            gameWon = true;
            gameLost = false;
            gameEndReason = event.getReason();
            postStateChangeEvent();
        }

        if (event.getSource() == contestant){
            playerUp = Coin.EMPTY;
            gameLost = true;
            gameWon = false;
            gameEndReason = event.getReason();
            postStateChangeEvent();
        }
    }
}
