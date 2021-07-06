package com.example.application;

import com.vaadin.flow.di.LookupInitializer;
import com.vaadin.flow.server.startup.LookupServletContainerInitializer;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.*;

public class HackedServletContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        System.err.println("XXXXXXXX IT WORKS");
        System.out.println("XXXXXXXX IT WORKS");

        //List<Class<?>> collection = Arrays.asList(new Class[]{LookupInitializer.class});
        //Set<Class<?>> classes = new HashSet<>(collection);
        //new LookupServletContainerInitializer().onStartup(classes, ctx);
    }
}
