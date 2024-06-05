package com.infraleap.views.dndtabs;

import com.infraleap.tab.CTab;
import com.infraleap.tab.CTabs;
import com.infraleap.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("DNDTabs")
@Route(value = "dndtabs", layout = MainLayout.class)
public class DNDTabsView extends VerticalLayout {

    public DNDTabsView() {

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
