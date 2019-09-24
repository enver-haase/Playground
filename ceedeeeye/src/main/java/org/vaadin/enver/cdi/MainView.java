package org.vaadin.enver.cdi;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.router.Route;
import org.vaadin.enver.backend.Backend;
import org.vaadin.enver.buttons.MyButton;
import org.vaadin.enver.layout.MainLayout;

import javax.inject.Inject;

/**
 * The main view contains a simple label element and a template element.
 */
@Route(value="", layout = MainLayout.class)
//@PWA(name = "Project Base for Vaadin Flow with CDI", shortName = "Project Base")
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {

    @Inject
    private MessageBean messageBean;

    @Inject
    private Backend backend;

    public MainView() {
        Button button = new MyButton("Click me",
                event -> Notification.show(messageBean.getMessage() + "###" + backend.getKingName()));
        add(button);
        button.setId("find-me-in-testing");

        //Util.dumpSystemProperties();

    }

}
