package org.vaadin.enver.ui.views.storefront.events;

import com.vaadin.flow.component.ComponentEvent;
import org.vaadin.enver.ui.views.orderedit.OrderItemsEditor;

public class ValueChangeEvent extends ComponentEvent<OrderItemsEditor> {

	public ValueChangeEvent(OrderItemsEditor component) {
		super(component, false);
	}
}