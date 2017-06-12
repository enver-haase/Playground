package com.infraleap.springcourse;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;

@SpringViewDisplay
public class AppViewDisplay extends Panel implements ViewDisplay{
    @Override
    public void showView(View view) {
        this.setContent((Component) view);
    }
}
