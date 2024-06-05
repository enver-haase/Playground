package com.infraleap.views.dndtabs;

import com.infraleap.views.MainLayout;
import com.infraleap.views.dndtabs.tab.DNDTab;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("DNDTabs")
@Route(value = "dndtabs", layout = MainLayout.class)
public class DNDTabsView extends VerticalLayout {

    public DNDTabsView() {

        Tabs horizontalDND = new Tabs(getTabs(10));
        add(horizontalDND);
    }

    static DNDTab[] getTabs(int count) {
        DNDTab[] tabs = new DNDTab[count];
        for (int i = 0; i < count; i++) {
            tabs[i] = new DNDTab();
            tabs[i].setLabel("Tab " + i);
        }
        return tabs;
    }

}
