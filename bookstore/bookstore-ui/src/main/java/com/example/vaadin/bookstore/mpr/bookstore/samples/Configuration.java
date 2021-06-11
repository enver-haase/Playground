package com.example.vaadin.bookstore.mpr.bookstore.samples;

import com.example.vaadin.bookstore.mpr.bookstore.BookstoreUI;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.mpr.core.LegacyUI;
import com.vaadin.mpr.core.MprTheme;
import com.vaadin.mpr.core.MprWidgetset;

@MprTheme("bookstoretheme")
@MprWidgetset("com.example.vaadin.bookstore.mpr.bookstore.BookstoreWidgetset")
@LegacyUI(BookstoreUI.class)
public class Configuration implements AppShellConfigurator {
}