package com.infraleap.tab;

import com.vaadin.flow.component.ClickNotifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.dnd.DropEffect;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.shared.Registration;

import java.io.Serial;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @see Tab
 */
public class CTab extends Tab implements DragSource<CTab>, DropTarget<CTab>, FlexComponent, ClickNotifier<CTab> {

	@Serial
	private static final long serialVersionUID = 1L;

	private final ArrayList<Registration> dragEventListeners = new ArrayList<>();
	private CTab dragSource;
	private int tabId;
	private int sourceId;


	/**
	 * {@inheritDoc}
	 *
	 * @see Tab#Tab()
	 */
	public CTab() {
		super();
	}


	/**
	 * @param drag boolean of the drag status
	 */
	public void setDragActive(boolean drag) {
		for (Registration listener : this.dragEventListeners) {
			listener.remove();
		}
		this.dragEventListeners.clear();
		setDraggable(drag);
		setActive(drag);

		if (!drag) {
			getElement().removeProperty("draggable");

		} else {
			// mouse
			Registration dragStartListener = addDragStartListener(event -> getParent().ifPresent(tabs -> {
				setDropEffect(DropEffect.MOVE);
				((CTabs) tabs).setTabTargetId(this.getElement().getNode().getId());
				((CTabs) tabs).setParentId(this.getParent().get().getElement().getNode().getId());
			}));
			this.dragEventListeners.add(dragStartListener);

			// mouse
			Registration dragEnterListener = getElement().addEventListener("dragenter", e -> handleDragAndDrop());
			this.dragEventListeners.add(dragEnterListener);
			// touch
			AtomicBoolean hasListener = new AtomicBoolean(false);
			Registration touchStartListener = getElement().addEventListener("touchstart",
					e -> getParent().ifPresent(tabs -> {
						setDropEffect(DropEffect.MOVE);

						((CTabs) tabs).setTabTargetId(this.getElement().getNode().getId());
						((CTabs) tabs).setParentId(this.getParent().get().getElement().getNode().getId());

						if (!hasListener.get()) {
							UI.getCurrent().getPage().executeJs("addTouchEndListener($0)", this.getElement());
							hasListener.set(true);
						}
					}));
			this.dragEventListeners.add(touchStartListener);

			// touch
			Registration dragEndListener = addDragEndListener(
					event -> getParent().ifPresent(tabs -> ((CTabs) tabs).setParentId(0)));
			this.dragEventListeners.add(dragEndListener);
		}
	}


	/**
	 * handle the drag and drop interaction
	 */
	private void handleDragAndDrop() {
		if (getParent().isPresent()) {
			CTabs tabs = (CTabs) getParent().get();
			int parentId = this.getParent().get().getElement().getNode().getId();
			int lastParentId = tabs.getParentId();

			if (this.isDraggable()) {
				if (parentId == lastParentId) {
					tabs.getChildren().forEach(tab -> {
						tabId = tab.getElement().getNode().getId();
						sourceId = tabs.getTabTargetId();

						if (sourceId == tabId) {
							dragSource = (CTab) tab;
						}
					});
					tabs.moveDragSource(dragSource, this);
				}

				if (parentId != lastParentId) {
					setDropEffect(DropEffect.NONE);
				}

				if (parentId != lastParentId) {
					tabs.setParentId(this.getParent().get().getElement().getNode().getId());
					tabs.setTabTargetId(dragSource.getElement().getNode().getId());
				} else {
					setDropEffect(DropEffect.NONE);
				}
			}
		}
	}
}
