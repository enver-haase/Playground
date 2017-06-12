package com.infraleap.springcourse;

import com.vaadin.ui.Component;

public interface AsComponent {
    default Component asComponent(){
        return (Component) this;
    };
}
