package com.infraleap.connect4;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.infraleap.connect4.event.*;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.server.SpringVaadinServlet;

import javax.servlet.ServletException;
import java.util.Collections;
import java.util.HashMap;
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

    private static final HashMap<VaadinSession, PlayerData> willingToPlay = new HashMap<>();

    private static final HashMap<VaadinSession, PlayerData> playing = new HashMap<>();

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

        VaadinSession session = event.getSession();

        vaadinSessions.remove(session);

        willingToPlay.remove(session);
        PlayerData loser = playing.remove(session);

        if (loser != null) {
            theEventBus.post(new PlayerAbortedEvent(this, loser));
        }

        postCurrentNumberOfSessions();
    }

    private void postCurrentNumberOfSessions(){
        theEventBus.post(new UpdateNumberOfSessionsEvent(this, getCurrentNumberOfSessions()));
    }

    public static int getCurrentNumberOfSessions(){
        return vaadinSessions.size();
    }


    @Subscribe
    public void handleContestantRequestEvent(ContestantRequestEvent event){
        synchronized (willingToPlay) {
            willingToPlay.put(event.getSource().getSession(), event.getSource());

            if (willingToPlay.size() >= 2) {

                System.out.println("CONNECTING TWO PLAYERS");

                VaadinSession[] sessions = willingToPlay.keySet().toArray(new VaadinSession[0]);

                PlayerData playerOne = willingToPlay.get(sessions[0]);
                PlayerData playerTwo = willingToPlay.get(sessions[1]);

                willingToPlay.remove(sessions[0]);
                willingToPlay.remove(sessions[1]);

                playing.put(sessions[0], playerOne);
                playing.put(sessions[1], playerTwo);

                theEventBus.post(new GameStartEvent(this, playerOne, playerTwo));
            }
        }
    }

}
