package com.infraleap.springcourse;

import com.vaadin.server.Resource;
import com.vaadin.spring.annotation.SpringComponent;

public interface MainMenu extends AsComponent {
    interface MenuItem{}

    MenuItem addMenuItem(String caption, Resource icon, String viewName);
    void removeMenuItem(MenuItem item);

}
