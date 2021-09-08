package com.example.application.views.helloworldflow;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

@Route(value = "hello-world-flow")
@PageTitle("Hello-World-Flow")
@CssImport("./views/helloworldflow/hello-world-flow-view.css")
public class HelloWorldFlowView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    private final Set<byte[]> bytes = new HashSet<>();

    public HelloWorldFlowView() {
        addClassName("hello-world-flow-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue()+"! Session size is: "+getSessionSize()+".");

            bytes.add(new byte[10000]);
        });
    }

    private long getSessionSize(){
        try {
            VaadinSession vaadinSession = VaadinSession.getCurrent();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(vaadinSession);
            oos.close();

            return baos.size();
        }
        catch (IOException ioException){
            ioException.printStackTrace();
        }
        return 0;
    }

}
