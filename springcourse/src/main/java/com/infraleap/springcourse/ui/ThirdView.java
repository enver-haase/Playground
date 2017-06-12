package com.infraleap.springcourse.ui;

import com.infraleap.springcourse.Constants;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;

@SpringView(name= Constants.THIRD)
public class ThirdView extends ThirdDesign implements View {
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
