package com.example.calendarpicker;

import java.util.Date;

import javax.servlet.annotation.WebServlet;

import com.example.calendarpicker.CalendarPicker.CalendarPickerListener;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("calendarpicker")
public class CalendarpickerUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = CalendarpickerUI.class, widgetset = "com.example.calendarpicker.CalendarpickerWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		CalendarPicker cp = new CalendarPicker();
		cp.setDate(new Date(83, 7, 26));
		cp.addDateChangeListener(new CalendarPickerListener() {
			
			@Override
			public void onDateChanged() {
				layout.addComponent(new Label("Now the date is: '"+cp.getDate().toString()+"'."));
			}
		});
		layout.addComponent(cp);

		layout.addComponent(new Label("Now the date is: '"+cp.getDate().toString()+"'."));
	}

}