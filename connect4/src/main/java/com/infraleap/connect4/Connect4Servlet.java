package com.infraleap.connect4;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.infraleap.connect4.event.GameStartEvent;
import com.infraleap.connect4.event.StartButtonClickedEvent;
import com.infraleap.connect4.event.UpdateNumberOfSessionsEvent;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.server.SpringVaadinServlet;

import javax.servlet.ServletException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@SpringComponent("vaadinServlet")
/**
 * This servlet handles session initialization and destruction.
 *
 * It also connects two sessions so that two players can play.
 */
public class Connect4Servlet extends SpringVaadinServlet implements SessionInitListener, SessionDestroyListener {

    private static final Set<VaadinSession> vaadinSessions = Collections.synchronizedSet(new HashSet<>());

    private static final Set<Connect4SessionState> willingToPlaySessions = Collections.synchronizedSet(new HashSet<>());

    public static final EventBus theEventBus = new EventBus();

    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(this);
        getService().addSessionDestroyListener(this);

        theEventBus.register(this);
    }

    @Override
    public void destroy(){
        super.destroy();
        theEventBus.unregister(this);
    }

    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {
        VaadinSession vSession = event.getSession();
        vaadinSessions.add(vSession);

        WrappedSession wSession = vSession.getSession();
        wSession.setMaxInactiveInterval(60);

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

    public static int getCurrentNumberOfSessions(){
        return vaadinSessions.size();
    }


    @Subscribe
    public void handleStartEvent(StartButtonClickedEvent event){
        synchronized (willingToPlaySessions) {
            willingToPlaySessions.add(event.getRequestor());

            if (willingToPlaySessions.size() >= 2) {
                Connect4SessionState[] players = willingToPlaySessions.toArray(new Connect4SessionState[0]);
                Connect4SessionState playerOne = players[0];
                Connect4SessionState playerTwo = players[1];
                willingToPlaySessions.remove(playerOne);
                willingToPlaySessions.remove(playerTwo);
                theEventBus.post(new GameStartEvent(this, playerOne, playerTwo));
            }
        }
    }

}
