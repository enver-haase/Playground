package com.infraleap.connect4;

import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@SpringComponent
public class Connect4SessionState {

    enum Coin {
        RED,
        YELLOW,
        EMPTY
    };

    private String playerName;
    private Coin[][] playfield;

    public Connect4SessionState(){
        this.playerName = "";

        this.playfield = new Coin[7][6];
        for (int col = 0; col < playfield.length; col++){
            for (int row = 0; row < playfield[col].length; row++)
            {
                playfield[col][row] = Coin.EMPTY;
            }
        }
    }


    public void setPlayerName(String playername){
        this.playerName = playername;
    }
    public String getPlayerName(){
        return playerName;
    }


    public void setPlayfield(Coin[][] playfield){
        this.playfield = playfield;
    }
    public Coin[][] getPlayfield(){
        return playfield;
    }

}
