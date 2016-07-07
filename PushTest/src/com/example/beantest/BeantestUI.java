package com.example.beantest;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.AbstractProperty;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.UIDetachedException;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("beantest")
@Push
public class BeantestUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = BeantestUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);
		
		BeanItemContainer<Bean> bic = Model.getInstance().getContainer();
		@SuppressWarnings("unchecked")
		AbstractProperty<Integer> valProp = (AbstractProperty<Integer>) bic.getItem(bic.getItemIds().get(0)).getItemProperty("val");
		Table table = new Table("Foo-Bar", bic);
		table.setImmediate(true);
		layout.addComponent(table);
	
		valProp.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				try{
				access(new Runnable(){
					@Override
					public void run() {
						// The push implementation only checks for things to send to the client
						// when the session lock is released (which is handled by UI.access)
						// That's why in every UI we run an empty Runnable through access() when
						// there is a new value.
					}});
				} catch (UIDetachedException uide){} // For Firefox Reload
			}
		});
		
	}

}