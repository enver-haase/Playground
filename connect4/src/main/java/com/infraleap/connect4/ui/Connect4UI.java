package com.infraleap.connect4.ui;

import com.google.common.eventbus.Subscribe;
import com.infraleap.connect4.Connect4Servlet;
import com.infraleap.connect4.Connect4SessionState;
import com.infraleap.connect4.event.*;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;


@Theme("connect4theme")
@SpringUI()
public class Connect4UI extends UI implements PlayfieldView.ColumnListener {

    @Autowired
    private Connect4SessionState state;

    @Autowired
    private HeaderView header;

    @Autowired
    private PlayfieldView playfield;

    @Override
    protected void init(VaadinRequest request) {
        // init() is called after attach, so we are attached to our session!
        Connect4Servlet.theEventBus.register(this);

        // Notice quickly if other UIs are closed
        setPollInterval(3000);

        // init behavior of the UI
        header.playerName.addValueChangeListener( event -> state.setPlayername(header.playerName.getValue()) );
        header.contestantName.setReadOnly(true);

        header.startButton.setDisableOnClick(true);
        header.startButton.addClickListener(event -> state.requestContestant(getSession()));

        // initial values, taken from the session state (copy other UIs' looks, share the same info across all UIs)
        updateUIFromState();
        header.numberUsersLabel.setValue(String.valueOf(Connect4Servlet.getCurrentNumberOfSessions()));


        // assemble the component tree
        Layout mainLayout = new VerticalLayout();
        mainLayout.addComponent(header);
        mainLayout.addComponent(playfield);
        setContent(mainLayout);
    }

    private void updateUIFromState(){
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

        if (!state.contestantRequested()){
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
                header.startButton.setCaption("Game in Progress...");
                header.startButton.setEnabled(false);
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
            System.out.println("UI '"+System.identityHashCode(this)+"' updating from state '"+System.identityHashCode(state)+"', session is '"+System.identityHashCode(this.getSession())+"'.");
            updateUIFromState();
        }
        else{
            System.out.println("IGNORING STATE CHANGE EVENT (source '"+System.identityHashCode(event.getSource())+"'): UI '"+System.identityHashCode(this)+"' NOT updating from state '"+System.identityHashCode(state)+"', session is '"+System.identityHashCode(this.getSession())+"'.");
        }
    }

    @Subscribe
    public void handleWon(GameWonEvent event){
        if (event.getSource() == state){
            Notification.show("Other player went away. YOU WIN!", Notification.Type.ERROR_MESSAGE); // error message must be clicked away.
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
        System.out.println("Column clicked: "+column);
    }
}
