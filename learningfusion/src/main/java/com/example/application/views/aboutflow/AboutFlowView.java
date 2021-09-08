package com.example.application.views.aboutflow;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.dependency.CssImport;

@Route(value = "about-flow")
@PageTitle("About-Flow")
@CssImport("./views/aboutflow/about-flow-view.css")
public class AboutFlowView extends Div {

    public AboutFlowView() {
        addClassName("about-flow-view");
        add(new Text("Content placeholder"));
    }

}
