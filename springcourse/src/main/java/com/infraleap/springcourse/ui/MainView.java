package com.infraleap.springcourse.ui;

import com.infraleap.springcourse.Constants;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;

@SpringView(name= Constants.FIRST)
public class MainView extends MainDesign implements View{
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
