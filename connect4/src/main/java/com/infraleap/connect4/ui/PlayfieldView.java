package com.infraleap.connect4.ui;

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
        private Coin(String val) {
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

    private void onColumnClicked(int column){
        System.err.println("Clicked: "+column);
        // TODO: eventbus notification of which column was clicked.

        //TODO: remove
        dropCoin(Coin.YELLOW, column);
    }

    private void checkWin(){
        // TODO: eventbus notification if we have a winner and which color.
    }
}
