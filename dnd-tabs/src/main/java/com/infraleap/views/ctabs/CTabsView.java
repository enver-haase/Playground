package com.infraleap.views.ctabs;

import com.infraleap.views.ctabs.tab.CTab;
import com.infraleap.views.ctabs.tab.CTabs;
import com.infraleap.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("CTabs")
@Route(value = "ctabs", layout = MainLayout.class)
public class CTabsView extends VerticalLayout {

    public CTabsView() {

        CTabs horizontalDND = new CTabs(getTabs(10));
        horizontalDND.setDraggable(true);
        add(horizontalDND);
    }

    static CTab[] getTabs(int count) {
        CTab[] tabs = new CTab[count];
        for (int i = 0; i < count; i++) {
            tabs[i] = new CTab();
            //tabs[i].add(new Span("Tab " + i));
            tabs[i].setLabel("Tab " + i);
        }
        return tabs;
    }

}
