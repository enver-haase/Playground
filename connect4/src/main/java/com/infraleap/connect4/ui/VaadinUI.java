package com.infraleap.connect4.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@Theme("connect4theme")
@SpringUI()
public class VaadinUI extends UI {
    @Autowired
    private PlayfieldView playfield;

    @Override
    protected void init(VaadinRequest request) {
        Layout mainLayout = new VerticalLayout();

        mainLayout.addComponent(new Button("Foo Bar Baz!!"));

        mainLayout.addComponent(playfield);

        setContent(mainLayout);
    }
}
