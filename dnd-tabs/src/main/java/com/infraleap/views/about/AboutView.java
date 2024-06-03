package com.infraleap.views.about;

import com.infraleap.tab.CTab;
import com.infraleap.tab.CTabs;
import com.infraleap.views.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
public class AboutView extends VerticalLayout {

    public AboutView() {

        CTabs horizontalDND = new CTabs(getTabs(10));
        horizontalDND.setDraggable(true);
        add(horizontalDND);
    }

    static CTab[] getTabs(int count) {
        CTab[] tabs = new CTab[count];
        for (int i = 0; i < count; i++) {
            tabs[i] = new CTab();
            tabs[i].add(new Span("Tab " + i));
        }
        return tabs;
    }

}
