package com.example.application.views.helloworld;

//import com.infraleap.animatecss.Animated;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;

@CssImport("./views/helloworld/hello-world-view.css")
@Route(value = "hello", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Hello World")
public class HelloWorldView extends HorizontalLayout /*implements Animated*/ {

    private TextField name;
    private Button sayHello;

    public HelloWorldView() {
        //animate(Animation.ROTATE_IN);

        addClassName("hello-world-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });

        sayHello.addClassName("color-by-width");

        Page page = UI.getCurrent().getPage();
        page.addBrowserWindowResizeListener(
                event -> {
                    sayHello.getElement().setVisible(event.getWidth() >= 500);
                }
        );
    }

}
