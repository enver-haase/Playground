package com.infraleap.connect4;

import com.google.common.eventbus.EventBus;
import com.infraleap.connect4.event.UpdateNumberOfSessionsEvent;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.server.SpringVaadinServlet;

import javax.servlet.ServletException;
import java.util.HashSet;
import java.util.Set;

@SpringComponent("vaadinServlet")
public class Connect4Servlet extends SpringVaadinServlet implements SessionInitListener, SessionDestroyListener {

    public static final String SESSION_KEY = "session";

    private Set<VaadinSession> vaadinSessions = new HashSet<>();

    private EventBus theEventBus = new EventBus();

    public EventBus getConnect4EventBus(){
        return theEventBus;
    }

    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(this);
        getService().addSessionDestroyListener(this);
    }

    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {
        VaadinSession vSession = event.getSession();
        vSession.setAttribute(SESSION_KEY, this);

        WrappedSession wSession = vSession.getSession();
        wSession.setMaxInactiveInterval(60);

        vaadinSessions.add(vSession);

        postCurrentNumberOfSessions();
    }

    @Override
    public void sessionDestroy(SessionDestroyEvent event) {
        vaadinSessions.remove(event.getSession());

        postCurrentNumberOfSessions();
    }

    private void postCurrentNumberOfSessions(){
        theEventBus.post(new UpdateNumberOfSessionsEvent(this, getCurrentNumberOfSessions()));
    }

    public int getCurrentNumberOfSessions(){
        return vaadinSessions.size();
    }
}
