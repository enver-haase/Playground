package com.example.application.views.hellojavahtml;

import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;

/**
 * A Designer generated component for the stub-tag template.
 *
 * Designer will add and remove fields with @Id mappings but does not overwrite
 * or otherwise change this file.
 */
@PageTitle("Hello Java+HTML")
@Route(value = "java-html")
@Tag("hello-java-html-view")
@JsModule("./views/hellojavahtml/hello-java-html-view.ts")
public class HelloJavaHTMLView extends LitTemplate {

    @Id
    private TextField name;

    @Id
    private Button sayHello;

    public HelloJavaHTMLView() {
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
    }
}
