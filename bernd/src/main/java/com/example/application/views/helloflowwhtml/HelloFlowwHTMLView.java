package com.example.application.views.helloflowwhtml;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

/**
 * A Designer generated component for the hello-floww-html-view template.
 *
 * Designer will add and remove fields with @Id mappings but does not overwrite
 * or otherwise change this file.
 */
@JsModule("./views/helloflowwhtml/hello-floww-html-view.ts")
@CssImport("./views/helloflowwhtml/hello-floww-html-view.css")
@Tag("hello-floww-html-view")
@Route(value = "hello-flowhtml")
@PageTitle("Hello Flow w/HTML")
public class HelloFlowwHTMLView extends LitTemplate {

    @Id
    private TextField name;

    @Id
    private Button sayHello;

    public HelloFlowwHTMLView() {
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
    }
}
