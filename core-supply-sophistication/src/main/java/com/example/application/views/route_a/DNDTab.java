package com.example.application.views.route_a;

import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.Tab;

public class DNDTab extends Tab implements DragSource<DNDTab>, DropTarget<DNDTab> {

    private Div page;

    public DNDTab(String string, String divText, boolean visible) {
        super(string);
        setDraggable(true); //(2)
        page = new Div();
        page.setText(divText);
        setDragData(page);
        page.setVisible(visible);

        setActive(true); // (2)

        addDropListener(eventA -> { // (3)
//			eventA.getDragSourceComponent().ifPresent(this::add);
            //   eventA.getDragData().ifPresent(data -> page = data; ));
            eventA.getDragSourceComponent().ifPresent(component -> {
                if (component instanceof  DNDTab) {
                    DNDTab sourceTab = (DNDTab) component;
                    System.out.println("Source Dropping Event: " + sourceTab.getLabel());
                    // swap these two tabs
                    // check if parents are the same
                    DNDTabs tabs = (DNDTabs) sourceTab.getParent().get();
                    tabs.swap(sourceTab, this);
                }
            });
            System.out.println("Destination Dropping Event: " + getLabel());
        });

    }

    public Div getPage() {
        return page;
    }

    public void setPage(Div page) {
        this.page = page;
    }
}
