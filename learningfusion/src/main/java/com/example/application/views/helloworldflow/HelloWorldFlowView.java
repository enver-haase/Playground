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


import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Route(value = "hello-world-flow")
@PageTitle("Hello-World-Flow")
@CssImport("./views/helloworldflow/hello-world-flow-view.css")
public class HelloWorldFlowView extends HorizontalLayout {

    private final TextField name;

    Logger logger = Logger.getLogger(HelloWorldFlowView.class.getName());

    private final SizeOf sizeOf = SizeOf.newInstance(true, false);

//    private final LoggingVisitorListener loggingVisitorListener = new LoggingVisitorListener();

    private static class Tree<T> extends TreeGrid<T> {
        Tree(ValueProvider<T, ?> valueProvider) {
            Column<T> only = addHierarchyColumn(valueProvider);
            only.setAutoWidth(true);
        }
    }

//    private class LoggingVisitorListener implements VisitorListener {
//        long currentSize = 0;
//        public void visited(Object object, long size) {
//            currentSize += size;
//            logger.log(Level.INFO, getDesc(object)+" cumulated size so far, from tree root: "+currentSize);
//        }
//    }
//
//    private static String getDesc(Object o){
//        return o.getClass().getName()+"@"+System.identityHashCode(o);
//    }

    public static class ObjectWrapper {

        public static ObjectWrapper wrap(Object o){
            return new ObjectWrapper(o);
        }

        private final Object o;
        public ObjectWrapper(Object o){
            this.o = o;
        }
        @Override
        public int hashCode(){
            return System.identityHashCode(o);
        }
        @Override
        public boolean equals(Object o){
            return this.o == o;
        }
    }

    public HelloWorldFlowView() {
        addClassName("hello-world-flow-view");
        name = new TextField("Your name");
        Button sayHello = new Button("Say hello");
        Tree<ObjectWrapper> treeGrid = new Tree<>(HelloWorldFlowView::getDescription);
        add(name, sayHello, treeGrid);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue()+"! Session size is: "+sizeOf.deepSizeOf(VaadinSession.getCurrent()));
            treeGrid.setItems(Collections.singleton(ObjectWrapper.wrap(VaadinSession.getCurrent())), sizeOf::getChildrenOfDesc);
        });
    }

    private static String getDescription(Object o){
        Object obj = o;
        while (obj instanceof ObjectWrapper){
            obj = ((ObjectWrapper) o).o;
        }
        return obj.getClass().getName()+"@"+System.identityHashCode(obj);
    }
}
