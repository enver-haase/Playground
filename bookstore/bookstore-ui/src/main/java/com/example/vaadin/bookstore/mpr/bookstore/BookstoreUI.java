package com.example.vaadin.bookstore.mpr.bookstore;

import com.example.vaadin.bookstore.mpr.bookstore.samples.MainScreen;
import com.example.vaadin.bookstore.mpr.bookstore.samples.authentication.AccessControl;
import com.example.vaadin.bookstore.mpr.bookstore.samples.authentication.BasicAccessControl;
import com.example.vaadin.bookstore.mpr.bookstore.samples.authentication.LoginScreen;
import com.example.vaadin.bookstore.mpr.bookstore.samples.authentication.LoginScreen.LoginListener;
import com.vaadin.mpr.MprUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;


/**
 * Main UI class of the application that shows either the login screen or the
 * main view of the application depending on whether a user is signed in.
 * <p>
 * The @Viewport annotation configures the viewport meta tags appropriately on
 * mobile devices. Instead of device based scaling (default), using responsive
 * layouts.
 */

public class BookstoreUI extends MprUI {

    private AccessControl accessControl = new BasicAccessControl();
    private Panel contentPanel;

    public BookstoreUI() {
        super();
        contentPanel = new Panel();
        contentPanel.addStyleName(ValoTheme.PANEL_BORDERLESS);
        contentPanel.addStyleName(ValoTheme.UI_WITH_MENU);
        contentPanel.setResponsive(true);
        contentPanel.addStyleName("legacy-content-root");
        contentPanel.setSizeFull();
    }

    public Panel getContentPanel() {
        return contentPanel;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        super.init(vaadinRequest);
        if (!accessControl.isUserSignedIn()) {
            contentPanel.setContent(new LoginScreen(accessControl, new LoginListener() {
                @Override
                public void loginSuccessful() {
                    showMainView();
                }
            }));
        } else {
            showMainView();
        }
    }

    protected void showMainView() {
        //addStyleName(ValoTheme.UI_WITH_MENU);
        contentPanel.setContent(new MainScreen(BookstoreUI.this));
        getNavigator().navigateTo(getNavigator().getState());
    }

    public static BookstoreUI get() {
        return (BookstoreUI) UI.getCurrent();
    }

    public AccessControl getAccessControl() {
        return accessControl;
    }

}
