package com.infraleap.springcourse;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.Resource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

//@UIScope // important!! (when not using external configuration)
//@SpringComponent
public class MobileMainMenuBean extends VerticalLayout implements MainMenu{

    private final List<MenuItemImpl> items = new ArrayList<>();

    @Autowired
    private Navigator nav;

    private class MenuItemImpl implements MenuItem{
        private String caption;
        private Resource icon;
        private String viewName;

        private MenuItemImpl(String caption, Resource icon, String viewName){
            this.caption = caption;
            this.icon = icon;
            this.viewName = viewName;
        }
    }

    @Override
    public MenuItem addMenuItem(String caption, Resource icon, String viewName) {
        MenuItemImpl item = new MenuItemImpl(caption, icon, viewName);
        this.items.add(item);
        update();
        return item;
    }

    @Override
    public void removeMenuItem(MenuItem item) {
        this.items.remove(item);

        update();
    }

    private void update(){
        this.removeAllComponents();
        for (MenuItemImpl item : items){
            Button menuEntry = new Button(item.caption);
            menuEntry.setIcon(item.icon);
            menuEntry.addClickListener(new Button.ClickListener(){

                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {
                    nav.navigateTo(item.viewName);
                }
            });
            this.addComponent(menuEntry);
        }
    }
}
