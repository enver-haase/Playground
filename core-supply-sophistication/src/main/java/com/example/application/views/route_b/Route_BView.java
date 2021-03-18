package com.example.application.views.route_b;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;

@CssImport("./views/route_b/route-b-view.css")
@Route(value = "route-b", layout = MainView.class)
@PageTitle("Route_B")
public class Route_BView extends Div {

    public Route_BView() {
        addClassName("route-b-view");
        add(new Text("Content placeholder"));
    }

}
