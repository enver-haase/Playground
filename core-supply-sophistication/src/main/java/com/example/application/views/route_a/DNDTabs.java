package com.example.application.views.route_a;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DNDTabs extends Tabs {

    Map<Tab, Component> tabsToPages = new HashMap<>();
    private final Div pages;

    public DNDTabs() {
        DNDTab tab1 = new DNDTab("Tab one", "Page 1", true);
        Div page1 = tab1.getPage();

        DNDTab tab2 = new DNDTab("Tab two", "Page#2", false);
        Div page2 = tab2.getPage();

        DNDTab tab3 = new DNDTab("Tab three", "Page#3", false);
        Div page3 = tab3.getPage();

        tabsToPages.put(tab1, page1);
        tabsToPages.put(tab2, page2);
        tabsToPages.put(tab3, page3);

        add(tab1,tab2,tab3);
        pages = new Div(page1, page2, page3);
        Set<Component> pagesShown = Stream.of(page1).collect(Collectors.toSet());

        addSelectedChangeListener(event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = tabsToPages.get(getSelectedTab());
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });
        setWidthFull();

        tab1.setSelected(true);
    }

    public Div getPages() {
        return pages;
    }


    public void swap(DNDTab sourceTab, DNDTab dndTab) {
        // should store selected tab and add it
        List<Component> components = getChildren().collect(Collectors.toList());
        int indexSource = components.indexOf(sourceTab);
        int indexDest = components.indexOf(dndTab);

        remove(sourceTab);
        remove(dndTab);
        if (indexDest > indexSource) {
            addComponentAtIndex(indexSource, dndTab);
            addComponentAtIndex(indexDest, sourceTab);
        } else {
            addComponentAtIndex(indexDest, sourceTab);
            addComponentAtIndex(indexSource, dndTab);
        }
    }
}
