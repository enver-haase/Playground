package com.example.application.views.route_a;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;

@CssImport("./views/route_a/route-a-view.css")
@Route(value = "route-a", layout = MainView.class)
@PageTitle("Route_A")
public class Route_AView extends Div {

    public Route_AView() {
        addClassName("route-a-view");
        add(new Text("Content placeholder"));
    }

}
