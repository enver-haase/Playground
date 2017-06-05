package com.infraleap.connect4.ui;

import com.google.common.eventbus.Subscribe;
import com.infraleap.connect4.Connect4Servlet;
import com.infraleap.connect4.Connect4SessionState;
import com.infraleap.connect4.event.*;
import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;


@Theme("connect4theme")
@SpringUI()
public class Connect4UI extends UI implements PlayfieldView.ColumnListener {

    @Autowired
    private Connect4SessionState state;

    @Autowired
    private HeaderView header;

    @Autowired
    private PlayfieldView playfield;

    @Autowired
    private FooterView footer;

    @Override
    protected void init(VaadinRequest request) {
        // init() is called after attach, so we are attached to our session!
        Connect4Servlet.theEventBus.register(this);

        // Notice quickly if other UIs are closed
        setPollInterval(3000);

        // init behavior of the UI
        header.playerName.addValueChangeListener( event -> state.setPlayername(header.playerName.getValue()) );
        header.playerName.setValueChangeMode(ValueChangeMode.EAGER);
        header.contestantName.setReadOnly(true);

        header.startButton.setDisableOnClick(true);
        header.startButton.addClickListener(event -> state.requestContestant(getSession()));

        // initial values, taken from the session state (copy other UIs' looks, share the same info across all UIs)
        updateUIFromState();
        header.numberUsersLabel.setValue(String.valueOf(Connect4Servlet.getCurrentNumberOfSessions()));


        // assemble the component tree
        Layout mainLayout = new VerticalLayout();
        mainLayout.addComponents(header, playfield, footer);
        setContent(mainLayout);

        playfield.addColumnListener(this);
    }

    private void updateUIFromState(){

        System.out.println(this.toString() + " ### " + state.getColor());

        setPlayerName(state.getPlayerName());

        setContestantName(state.getContestantName());

        playfield.clear();
        Connect4SessionState.Coin[][] playfield_state = state.getPlayfield(); // [col][row]
        for (int row = playfield_state[0].length -1; row >= 0; row--){
            for (int col = 0; col < playfield_state.length; col++){
                if (playfield_state[col][row] == Connect4SessionState.Coin.YELLOW){
                    playfield.dropCoin(PlayfieldView.Coin.YELLOW, col);
                } else if (playfield_state[col][row] == Connect4SessionState.Coin.RED){
                    playfield.dropCoin(PlayfieldView.Coin.RED, col);
                }
            }
        }

        if (state.getGameLost()){
            header.startButton.setCaption("GAME LOST: "+state.getGameEndReason());
            header.startButton.setEnabled(false);
        } else if (state.getGameWon()){
            header.startButton.setCaption("GAME WON: "+state.getGameEndReason());
            header.startButton.setEnabled(false);
        } else if (!state.contestantRequested()){
            header.startButton.setCaption("Challenge A Contestant");
            header.startButton.setEnabled(true);
        }
        else{
            boolean waitingForContestant = (state.getColor() == Connect4SessionState.Coin.EMPTY);
            if (waitingForContestant){
                header.startButton.setCaption("Waiting for Contestant...");
                header.startButton.setEnabled(false);
            }
            else{
                if (state.getPlayerUp() == state.getColor()){
                    header.startButton.setCaption("(YOUR MOVE)");
                }
                else{
                    header.startButton.setCaption("(WAITING FOR MOVE)");
                }
                header.startButton.setEnabled(false);
                String colorName = state.getColor() == Connect4SessionState.Coin.RED? "RED" : "YELLOW";
                footer.colorDisplayButton.setCaption("Your color: " + colorName);
            }
        }
    }


    @Override
    public void detach(){
        // attach is called before init()
        Connect4Servlet.theEventBus.unregister(this);
        super.detach();
    }

    @Subscribe
    public void handleUpdateNumberOfSessions(UpdateNumberOfSessionsEvent e){
        header.numberUsersLabel.setValue(String.valueOf(e.getNumberOfSessions()));
    }

    @Subscribe
    public void handleStateChange(StateChangeEvent event){
        if (event.getSource() == state){
            //System.out.println("UI '"+System.identityHashCode(this)+"' updating from state '"+System.identityHashCode(state)+"', session is '"+System.identityHashCode(this.getSession())+"'.");
            updateUIFromState();
        }
        else{
            setContestantName(state.getContestantName());
            //System.out.println("IGNORING STATE CHANGE EVENT (source '"+System.identityHashCode(event.getSource())+"'): UI '"+System.identityHashCode(this)+"' NOT updating from state '"+System.identityHashCode(state)+"', session is '"+System.identityHashCode(this.getSession())+"'.");
        }
    }

    private void setContestantName(String name){
        if (!name.equals(header.contestantName.getCaption())) {
            header.contestantName.setReadOnly(false);
            header.contestantName.setValue(name);
            header.contestantName.setReadOnly(true);
        }
    }

    private void setPlayerName(String name)
    {
        if (!name.equals(header.playerName.getValue())) {
            header.playerName.setValue(name);
        }
    }

    @Override
    public void onColumnClicked(int column) {
        Connect4SessionState.Coin ourColor = state.getColor();
        if (state.getPlayerUp() != Connect4SessionState.Coin.EMPTY) /* if game is in progress */ {
            if (state.getPlayerUp() == state.getColor()) { /* if it is our turn */
                PlayfieldView.Coin pfCoin = (ourColor == Connect4SessionState.Coin.YELLOW ? PlayfieldView.Coin.YELLOW : PlayfieldView.Coin.RED);
                try {
                    this.playfield.dropCoin(pfCoin, column);
                    Connect4Servlet.theEventBus.post(new MoveMadeEvent(state, column)); /* update other UIs, swap plwayer turns */
                } catch (IllegalArgumentException exc) {
                    // do nothing. This column is full.
                }

                if (playfield.gameWon(pfCoin)) {
                    Connect4Servlet.theEventBus.post(new GameWonEvent(state, "Four in a Row!"));
                }
            }
        }
    }
}
