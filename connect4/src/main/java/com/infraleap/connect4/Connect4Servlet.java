package com.infraleap.connect4;

import com.infraleap.connect4.ui.Connect4UI;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.UI;

import javax.servlet.ServletException;
import java.util.HashSet;
import java.util.Set;

@SpringComponent("vaadinServlet")
public class Connect4Servlet extends SpringVaadinServlet implements SessionInitListener, SessionDestroyListener {

    private static Set<VaadinSession> vaadinSessions = new HashSet<>();

    private static Thread pusher = new Thread(){
        {
            this.start();
        }

        @Override
        public void run(){
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }

                for (VaadinSession vaadinSession : vaadinSessions) {
                    for (UI ui : vaadinSession.getUIs()){
                        ((Connect4UI) ui).pushNumSessions(vaadinSessions.size());
                    }
                }
            }
        }
    };

    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(this);
        getService().addSessionDestroyListener(this);
    }

    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {
        VaadinSession vSession = event.getSession();
        WrappedSession wSession = vSession.getSession();
        wSession.setMaxInactiveInterval(60);

        vaadinSessions.add(vSession);
    }

    @Override
    public void sessionDestroy(SessionDestroyEvent event) {
        vaadinSessions.remove(event.getSession());
    }
}
