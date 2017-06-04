package com.infraleap.connect4.ui;

import com.infraleap.connect4.Connect4Servlet;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;

import java.util.function.Consumer;

@SpringComponent
@UIScope
public class PlayfieldView extends PlayfieldDesign {
    public PlayfieldView() {
        super();

        clear();

        for (int col = 0; col < getColumns(); col++){
            for (int row = 0; row < getRows(); row++){
                final int columnClicked = col;
                Image image = (Image) getComponent(col, row);
                image.addClickListener(event -> {if (event.getButton().equals(MouseEventDetails.MouseButton.LEFT)) onColumnClicked(columnClicked);});
            }
        }
    }


    public enum Coin{
        RED("img/Red.png"),
        YELLOW("img/Yellow.png"),
        EMPTY("img/Empty.png");

        private final ThemeResource value;
        Coin(String val) {
            this.value = new ThemeResource(val);
        }
    }

    public void dropCoin(Coin coin, int column){
        if (column >= getColumns() || column < 0){
            throw new IllegalArgumentException("Column argument out of range.");
        }
        for (int row = getRows()-1; row >= 0; row--){
            Image target = (Image) getComponent(column, row);
            if (target.getSource() == Coin.EMPTY.value){
                target.setSource(coin.value);
                return;
            }
        }
        throw new IllegalArgumentException("Column already full.");
    }

    public void clear(){
        forEach(new Consumer<Component>() {
            @Override
            public void accept(Component component) {
                ((Image) component).setSource(Coin.EMPTY.value);
            }
        });
    }

    public boolean yellowWon(){
        return won(Coin.YELLOW);
    }

    public boolean redWon(){
        return won(Coin.RED);
    }

    private boolean won(Coin coin){

        final int numCols = getColumns();
        final int numRows = getRows();

        // where there is a coin of the colour of 'coin', there's a 'true'.
        boolean[][] board = new boolean[numCols][numRows];
        for (int col = 0; col<numCols; col++){
            for (int row = 0; row<numRows; row++){
                board[col][row] = ((Image) getComponent(col, row)).getSource() == coin.value;
            }
        }

        // horizontalCheck
        for (int j = 0; j<numRows-3 ; j++ ){
            for (int i = 0; i<numCols; i++){
                if (board[i][j] && board[i][j+1] && board[i][j+2] && board[i][j+3]){
                    return true;
                }
            }
        }
        // verticalCheck
        for (int i = 0; i<numCols-3 ; i++ ){
            for (int j = 0; j<numRows; j++){
                if (board[i][j] && board[i+1][j] && board[i+2][j] && board[i+3][j]){
                    return true;
                }
            }
        }
        // ascendingDiagonalCheck
        for (int i=3; i<numCols; i++){
            for (int j=0; j<numRows-3; j++){
                if (board[i][j] && board[i-1][j+1] && board[i-2][j+2] && board[i-3][j+3]) {
                    return true;
                }
            }
        }
        // descendingDiagonalCheck
        for (int i=3; i<numCols; i++){
            for (int j=3; j<numRows; j++){
                if (board[i][j] && board[i-1][j-1] && board[i-2][j-2] && board[i-3][j-3]) {
                    return true;
                }
            }
        }

        return false;
    }

    private void onColumnClicked(int column){
        System.err.println("Clicked: "+column);
        // TODO: eventbus notification of which column was clicked.

        //TODO: remove
        dropCoin(Coin.YELLOW, column);
        System.out.println(yellowWon());

    }

    private void checkWin(){
        // TODO: eventbus notification if we have a winner and which color.
    }
}
