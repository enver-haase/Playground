package com.infraleap.crudui;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class Greeter {
    String sayHello() {
        return "Hello from bean " + toString();
    }
}
