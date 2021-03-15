package com.example.application.views.helloflow;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;

@CssImport("./views/helloflow/hello-flow-view.css")
@Route(value = "hello-flow")
@RouteAlias(value = "")
@PageTitle("Hello Flow")
public class HelloFlowView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public HelloFlowView() {
        addClassName("hello-flow-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
    }

}
