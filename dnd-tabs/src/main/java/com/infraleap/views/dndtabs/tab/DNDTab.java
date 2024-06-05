package com.infraleap.views.dndtabs.tab;

import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.tabs.Tab;

public class DNDTab extends Tab implements DragSource<Tab>, DropTarget<Tab> {
	public DNDTab(){
		super();
		this.setDraggable(true);
		this.setActive(true);

		addDropListener( e -> {
			e.getDragSourceComponent().ifPresent( source -> {
				String srcLabel = ((Tab) source).getLabel();
				Notification.show("Dropped " + srcLabel + " on " + this.getLabel());

				// Reorder tabs, without changing their positions in the DOM
				// While here we are just swapping the labels, in a real-world scenario
				// all data pertaining to a tab can be made swappable in a similar way,
				// by exposing the associated data in the Tab's derived daughter class.
				((Tab) source).setLabel(this.getLabel());
				this.setLabel(srcLabel);
			});
		});
	}
}
