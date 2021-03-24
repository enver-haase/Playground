package com.example.application.views.route_a;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;

@CssImport("./views/route_a/route-a-view.css")
@Route(value = "route-a", layout = MainView.class)
@PageTitle("Route_A")
public class Route_AView extends Div {

    public Route_AView() {
        //addClassName("route-a-view");
        //add(new Text("Content placeholder"));

        DNDTabs dndTabs = new DNDTabs();
        dndTabs.setSizeFull();
        add(dndTabs);

        //DNDTab tab1 = new DNDTab("EINS", "one", true);
        //DNDTab tab2 = new DNDTab("ZWEI", "two", true);
        //DNDTab tab3 = new DNDTab("DREI", "three", true);
        //dndTabs.add(tab1, tab2, tab3);

    }

}
