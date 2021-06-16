package com.example.vaadin.bookstore.mpr.bookstore;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.mpr.LegacyWrapper;
import com.vaadin.mpr.core.LegacyUI;
import com.vaadin.mpr.core.MprTheme;
import com.vaadin.mpr.core.MprWidgetset;

//@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@MprTheme("bookstoretheme")
@MprWidgetset("com.example.vaadin.bookstore.mpr.bookstore.BookstoreWidgetset")
@Route("")
@LegacyUI(BookstoreUI.class)
@PageTitle("Bookstore")
public class BookstoreMainLayout extends Div {

    private LegacyWrapper legacyWrapper;

    public BookstoreMainLayout() {
        setSizeFull();

    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        //Should not be needed after https://github.com/vaadin/multiplatform-runtime/issues/23 is fixed, will slow down resizing considerably
        getUI().get().getPage().executeJavaScript("setTimeout(function() { window.addEventListener('resize', vaadin.forceLayout); }, 1000)");

        LegacyWrapper legacyWrapper = new LegacyWrapper(BookstoreUI.get().getContentPanel());
        legacyWrapper.addClassName("legacy-wrapper");
        legacyWrapper.setSizeFull();
        add(legacyWrapper);

    }
}
