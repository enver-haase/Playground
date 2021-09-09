package com.example.application.views.helloworldflow;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.server.VaadinSession;
import org.ehcache.sizeof.SizeOf;
import org.ehcache.sizeof.VisitorListener;
//import org.ehcache.sizeof.impl.AgentSizeOf;
//import org.ehcache.sizeof.impl.PassThroughFilter;
//import org.ehcache.sizeof.impl.ReflectionSizeOf;
//import org.ehcache.sizeof.impl.UnsafeSizeOf;


import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Route(value = "hello-world-flow")
@PageTitle("Hello-World-Flow")
@CssImport("./views/helloworldflow/hello-world-flow-view.css")
public class HelloWorldFlowView extends HorizontalLayout {

    private final TextField name;

    Logger logger = Logger.getLogger(HelloWorldFlowView.class.getName());

    private final SizeOf sizeOf = SizeOf.newInstance(true, true); // filters can be passed here
    //private final SizeOf sizeOf = new AgentSizeOf(new PassThroughFilter(),true, true); // filters can be passed here
    //private final SizeOf sizeOf = new UnsafeSizeOf(new PassThroughFilter(),true, true); // filters can be passed here
    //private final SizeOf sizeOf = new ReflectionSizeOf(new PassThroughFilter(),true, true); // filters can be passed here

    private final LoggingVisitorListener loggingVisitorListener = new LoggingVisitorListener();

    private static class Tree<T> extends TreeGrid<T> {
        Tree(ValueProvider<T, ?> valueProvider) {
            Column<T> only = addHierarchyColumn(valueProvider);
            only.setAutoWidth(true);
        }
    }

    private class LoggingVisitorListener implements VisitorListener {
        private int depth = 0;
        public void visited(Object object, long size) {
            depth++;
            logger.log(Level.INFO, getDescription(object)+": "+ (sizeOf.deepSizeOf(object)));
        }
    }


    public HelloWorldFlowView() {
        addClassName("hello-world-flow-view");
        name = new TextField("Your name");
        Button sayHello = new Button("Say hello");
        Tree<Object> treeGrid = new Tree<>(HelloWorldFlowView::getDescription);
        add(name, sayHello, treeGrid);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue()+"! Session size is: "+sizeOf.deepSizeOf(loggingVisitorListener, VaadinSession.getCurrent()));
            treeGrid.setItems(Collections.singleton(VaadinSession.getCurrent()), parent -> new LinkedList<>()); // TODO
            logger.log(Level.SEVERE, "Depth is "+loggingVisitorListener.depth);
        });
    }

    private static String getDescription(Object o){
        return o.getClass().getName()+"@"+System.identityHashCode(o);
    }
}
