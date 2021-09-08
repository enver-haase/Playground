package com.example.application.views.helloworldflow;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.server.VaadinSession;

import org.ehcache.sizeof.SizeOf;
import org.ehcache.sizeof.VisitorListener;
import org.ehcache.sizeof.filters.SizeOfFilter;


import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Route(value = "hello-world-flow")
@PageTitle("Hello-World-Flow")
@CssImport("./views/helloworldflow/hello-world-flow-view.css")
public class HelloWorldFlowView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    private final Set<byte[]> bytes = new HashSet<>();

    Logger logger = Logger.getLogger(HelloWorldFlowView.class.getName());

    private class DependencyWalker implements SizeOfFilter{
        @Override
        public Collection<Field> filterFields(Class<?> aClass, Collection<Field> collection) {
            logger.log(Level.INFO, "Visiting "+collection.size()+" field(s) of class "+aClass.getName()+":");
            for (Field field : collection){
                logger.log(Level.INFO, field.getType().getName()+" --- "+field.getName());
            }
            logger.log(Level.INFO, "Done visiting "+collection.size()+" field(s) of class "+aClass.getName());
            return collection;
        }

        @Override
        public boolean filterClass(Class<?> aClass) {
            logger.log(Level.INFO, "Visiting class "+aClass.getName());
            return true;
        }
    }

    private class MyVisitorListener implements VisitorListener {
        private long currentDepth;
        private long currentSize;
        @Override
        public void visited(Object o, long size) {
            currentDepth += 1;
            currentSize += size;
            logger.log(Level.INFO, "Depth "+currentDepth+", currentSize "+currentSize+" --- Object "+System.identityHashCode(o)+" of class "+o.getClass().getName()+" visited, size "+size);
        }
    }

    public HelloWorldFlowView() {
        addClassName("hello-world-flow-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        TreeGrid treeGrid = new TreeGrid<>();
        add(name, sayHello, treeGrid);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue()+"! Session size is: "+SizeOf.newInstance(false, false, new DependencyWalker()).deepSizeOf(new MyVisitorListener(), VaadinSession.getCurrent()));

            bytes.add(new byte[10000]);
        });
    }


}
