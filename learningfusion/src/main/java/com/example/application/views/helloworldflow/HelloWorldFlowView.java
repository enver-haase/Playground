package com.example.application.views.helloworldflow;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.dependency.CssImport;

@Route(value = "hello-world-flow")
@RouteAlias(value = "")
@PageTitle("Hello-World-Flow")
@CssImport("./views/helloworldflow/hello-world-flow-view.css")
public class HelloWorldFlowView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public HelloWorldFlowView() {
        addClassName("hello-world-flow-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
    }

}
