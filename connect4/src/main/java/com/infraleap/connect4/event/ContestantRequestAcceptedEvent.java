package com.infraleap.connect4.event;

import com.vaadin.server.VaadinSession;

import java.util.EventObject;

public class ContestantRequestAcceptedEvent extends EventObject {
    private VaadinSession requestor;
    public ContestantRequestAcceptedEvent(VaadinSession acceptor, VaadinSession requestor){
        super(acceptor);
        this.requestor = requestor;
    }
}
