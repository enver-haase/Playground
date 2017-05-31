package com.infraleap.connect4.ui;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;

import java.util.function.Consumer;

@SpringComponent
@UIScope
public class PlayfieldView extends PlayfieldDesign {
    public PlayfieldView() {
        super();

        for (int col = 0; col < getColumns(); col++){
            for (int row = 0; row < getRows(); row++){
                final int columnClicked = col;
                Image image = (Image) getComponent(col, row);
                image.addClickListener(event -> {if (event.getButton().equals(MouseEventDetails.MouseButton.LEFT)) onColumnClicked(columnClicked);});
            }
        }
    }

    private void onColumnClicked(int column){
        System.err.println("Clicked: "+column);
    }
}
