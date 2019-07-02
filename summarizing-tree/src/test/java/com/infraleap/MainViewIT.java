package com.infraleap;

import com.vaadin.flow.component.grid.testbench.GridElement;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.flow.theme.lumo.Lumo;

public class MainViewIT extends AbstractViewTest {

    @Test
    public void gridIsUsingLumoTheme() {
        WebElement element = $(GridElement.class).first();
        assertThemePresentOnElement(element, Lumo.class);
    }
}
