package com.example.application.views.styling;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;

@CssImport("./views/styling/styling-view.css")
@Route(value = "Styling", layout = MainView.class)
@PageTitle("Styling")
public class StylingView extends Div {

    public StylingView() {
        addClassName("styling-view");
        add(new Text("Content placeholder"));
    }

}
