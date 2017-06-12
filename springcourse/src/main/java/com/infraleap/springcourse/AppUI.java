package com.infraleap.springcourse;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
public class AppUI extends UI {

    @Autowired
    private MainMenu mainMenu;

    @Autowired
    ViewDisplay vd;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        VerticalLayout layout = new VerticalLayout();
        setContent(layout);

        layout.addComponent(new Label("Hallo World"));

        layout.addComponent(mainMenu.asComponent());

        mainMenu.addMenuItem("First", VaadinIcons.ABACUS, Constants.FIRST);
        mainMenu.addMenuItem("Another", VaadinIcons.AMBULANCE, Constants.SECOND);
        mainMenu.addMenuItem("3rd", VaadinIcons.AIRPLANE, Constants.THIRD);

        layout.addComponent((Component) vd); // TODO: remove cast

    }
}
