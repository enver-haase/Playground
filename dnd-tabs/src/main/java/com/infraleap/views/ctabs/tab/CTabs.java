package com.infraleap.views.ctabs.tab;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.dom.DomListenerRegistration;

import java.io.Serial;
import java.util.ArrayList;

/**
 * @see Tabs
 */
public class CTabs extends Tabs {

	@Serial
	private static final long serialVersionUID = 1L;
	private final ArrayList<DomListenerRegistration> dragEventListeners = new ArrayList<>();
	private int tabTargetId;
	private int parentId;
	private TabsChangedEvent dragAndDropEvent;
	private Tab previousSelectedTab = null;


	/**
	 * {@inheritDoc}
	 *
	 * @param tabs the given tabs
	 * @see Tabs#Tabs(Tab...)
	 */
	public CTabs(CTab... tabs) {
		super(tabs);
	}


	@Override
	public Tab getSelectedTab() {
		Tab result = null;
		try {
			result = super.getSelectedTab();

			if (result != null) {
				setPreviousSelectedTab(result);
			} else {
				handleFallbackSelectedTab();
			}

		} catch (IllegalStateException iae) {
			handleFallbackSelectedTab();
		}

		return result;
	}


	@Override
	public void setSelectedTab(Tab selectedTab) {
		if (getChildren().anyMatch(comp -> comp.equals(selectedTab))) {
			super.setSelectedTab(selectedTab);
		}
	}


	/**
	 * handle the selected tab
	 */
	private void handleFallbackSelectedTab() {
		if (this.previousSelectedTab != null) {
			setSelectedTab(this.previousSelectedTab);
		} else {
			setSelectedTab(null);
		}
	}


	/**
	 * set the previous selected tab
	 *
	 * @param previousSelectedTab the given tab
	 */
	private void setPreviousSelectedTab(Tab previousSelectedTab) {
		this.previousSelectedTab = previousSelectedTab;
	}


	/**
	 * @return the tab target id
	 */
	public int getTabTargetId() {
		return tabTargetId;
	}


	/**
	 * @param tabTargetId the tag target id
	 */
	public void setTabTargetId(int tabTargetId) {
		this.tabTargetId = tabTargetId;
	}


	/**
	 * @return the tab parent id
	 */
	public int getParentId() {
		return parentId;
	}


	/**
	 * @param parentId the tab parent id
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}


	/**
	 * @param dragSource source
	 * @param dropSource source
	 */
	public void moveDragSource(CTab dragSource, CTab dropSource) {
		createTabChangedEvent(dragSource, dropSource);
		replace(dragSource, dropSource);
		fireEvent(dragAndDropEvent);
		setSelectedTab(dragSource);
	}


	/**
	 * @param dragSource the drag source
	 * @param dropSource the drop source
	 */
	private void createTabChangedEvent(CTab dragSource, CTab dropSource) {
		dragAndDropEvent = new TabsChangedEvent(this, true);
		dragAndDropEvent.setDragIndex(indexOf(dragSource));
		dragAndDropEvent.setDropIndex(indexOf(dropSource));
	}


	/**
	 * @param draggableState the draggable status
	 */
	public void setDraggable(boolean draggableState) {
		for (DomListenerRegistration listener : this.dragEventListeners) {
			listener.remove();
		}
		this.dragEventListeners.clear();
		getChildren().forEach(child -> {
			if (child instanceof CTab item) {
				item.setDragActive(draggableState);

				DomListenerRegistration dragoverListener = child.getElement().addEventListener("dragover", e -> {
				}).addEventData("event.preventDefault()");
				this.dragEventListeners.add(dragoverListener);
				DomListenerRegistration dragEndListener = child.getElement().addEventListener("dragend", e -> {
				});
				this.dragEventListeners.add(dragEndListener);
				DomListenerRegistration touchMoveListener = child.getElement().addEventListener("touchmove", e -> {
				}).addEventData("event.preventDefault()");
				this.dragEventListeners.add(touchMoveListener);
			}
		});
	}


	public static class TabsChangedEvent extends ComponentEvent<CTabs> {
		int dragIndex;
		int dropIndex;


		public TabsChangedEvent(CTabs source, boolean fromClient) {
			super(source, fromClient);
		}

		public void setDragIndex(int dragIndex) {
			this.dragIndex = dragIndex;
		}

		public void setDropIndex(int dropIndex) {
			this.dropIndex = dropIndex;
		}
	}
}
