package org.vaadin.enver.layout;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.RouterLayout;

import java.util.Objects;

public class MainLayout extends VerticalLayout implements RouterLayout {


    private VerticalLayout contentPlaceholder;

    /**
     * Creates a new MachLayout.
     */
    public MainLayout(){
        // You can initialise any data required for the connected UI components here.
        add(new H2("Hallo Welt!"));
        add(contentPlaceholder = new VerticalLayout());
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        if (content != null) {
            contentPlaceholder.getElement().appendChild((Element) Objects.requireNonNull(content.getElement()));
        }
    }
}
