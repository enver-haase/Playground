package com.example.application.views.styling;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;

@CssImport("./views/styling/styling-view.css")
@Route(value = "styling", layout = MainView.class)
@PageTitle("Styling")
public class StylingView extends Div {

    public StylingView() {


        SplitLayout splitLayout = new SplitLayout();

        add(splitLayout);
        splitLayout.setSizeFull();

        TextArea textArea1 = new TextArea();
        splitLayout.setPrimaryStyle("min-width", "400px");
        splitLayout.addToPrimary(textArea1);


        TextArea textArea2 = new TextArea();
        splitLayout.setSecondaryStyle("min-width", "400px");
        splitLayout.addToSecondary(textArea2);
    }

}
