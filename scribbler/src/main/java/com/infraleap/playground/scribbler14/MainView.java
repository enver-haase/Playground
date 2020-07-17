package com.infraleap.playground.scribbler14;

import com.infraleap.vaadin.scribble.ScribblePane;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.mpr.LegacyWrapper;
import com.vaadin.mpr.core.HasLegacyComponents;
import com.vaadin.mpr.core.MprWidgetset;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Image;

import java.util.Date;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@MprWidgetset("com.infraleap.vaadin.scribbler.WidgetSet")
public class MainView extends VerticalLayout implements HasLegacyComponents {

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     */
    public MainView() {

        // Use TextField for standard text input
        TextField textField = new TextField("Your name");
        textField.addThemeName("bordered");

        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        addClassName("centered-content");
        add(textField);



        com.vaadin.ui.VerticalLayout layout = new com.vaadin.ui.VerticalLayout();
        // Initialize our new UI scribblePane
        final ScribblePane scribblePane = new ScribblePane() {
            @Override
            public void onImageDataFromClient(int width, int height, int[] rgba) {
                layout.addComponent(new com.vaadin.ui.Label("Got an image ("+width+"x"+height+")!")); // XXX

                StreamResource.StreamSource imageSource = new RGBImageSource(width, height, rgba);
                StreamResource resource = new StreamResource(imageSource, "image-"+width+"x"+height+".png");
                Image image = new Image("("+width+"x"+height + ") - " + new Date(), resource);
                layout.addComponent(image);
            }
        };

        layout.setMargin(false);
        layout.setSpacing(false);
        layout.addComponent(scribblePane);

        com.vaadin.ui.Button snapButton = new com.vaadin.ui.Button("Snapshot!", clickEvent -> scribblePane.requestImageFromClient());
        com.vaadin.ui.Button clearButton = new com.vaadin.ui.Button ("Clear!", clickEvent -> scribblePane.clearImage());

        com.vaadin.ui.HorizontalLayout hl = new com.vaadin.ui.HorizontalLayout(snapButton, clearButton);
        layout.addComponent(hl);

        LegacyWrapper lw = new LegacyWrapper(layout);
        lw.setHeight("500px");
        add(lw);

    }

}
