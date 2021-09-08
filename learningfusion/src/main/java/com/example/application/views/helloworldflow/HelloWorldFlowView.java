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
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@Route(value = "hello-world-flow")
@PageTitle("Hello-World-Flow")
@CssImport("./views/helloworldflow/hello-world-flow-view.css")
public class HelloWorldFlowView extends HorizontalLayout {

    private static final long INTERESTING_SIZE_MINIMUM = 1024;

    private final TextField name;

    Logger logger = Logger.getLogger(HelloWorldFlowView.class.getName());

    private final IdentityHashMap<Object, Long> sizes = new IdentityHashMap<>();

    private final SizeOf sizeOf = SizeOf.newInstance(true, false, new SelfExcludingSizeOfFilter());

    private final LoggingVisitorListener loggingVisitorListener = new LoggingVisitorListener();

    private class SelfExcludingSizeOfFilter implements SizeOfFilter{
        @Override
        public Collection<Field> filterFields(Class<?> aClass, Collection<Field> collection) {
//            logger.log(Level.INFO, "Visiting "+collection.size()+" field(s) of class "+aClass.getName()+":");
//            for (Field field : collection){
//                logger.log(Level.INFO, field.getType().getName()+" --- "+field.getName());
//            }
//            logger.log(Level.INFO, "Done visiting "+collection.size()+" field(s) of class "+aClass.getName());
            return collection;
        }

        @Override
        public boolean filterClass(Class<?> aClass) {
            String[] excludes = {HelloWorldFlowView.class.getName(), sizes.getClass().getName(), "org.apache.tomcat", "org.springframework.boot", "java."};
            for (String exclude : excludes){
                if (aClass.getName().contains(exclude)){
                    return false;
                }
            }
            return true;
        }
    }

    private class LoggingVisitorListener implements VisitorListener {
        @Override
        public void visited(Object o, long size) {

            if (!sizes.containsKey(o)) {
                sizes.put(o, sizeOf.deepSizeOf(o)); // 'size' is only flat!
            }
            else{
                return; // we've been here.
            }
            long deepSize = sizes.get(o);

            if (deepSize >= INTERESTING_SIZE_MINIMUM) {
                logger.log(Level.INFO, o.getClass().getName()+"@"+System.identityHashCode(o)+" size "+deepSize);

                Field[] fields = o.getClass().getFields();

                if (fields.length != 0) {
                    for (Field f : fields) {
                        StringBuilder fieldOutput = new StringBuilder();
                        fieldOutput.append(f.getType().getName()).append(" ").append(f.getName());
                        if (f.isAccessible()) {
                            if (!f.getType().isPrimitive()) {
                                try {
                                    Object value = f.get(Modifier.isStatic(f.getModifiers()) ? null : o);
                                    if (!sizes.containsKey(value)) {
                                        sizes.put(value, sizeOf.deepSizeOf(value));
                                    }
                                    else {
                                        fieldOutput.append(" size: ").append(sizes.get(value));
                                    }
                                } catch (IllegalAccessException accessException) {
                                    logger.log(Level.WARNING, "Cannot access above field.", accessException);
                                }
                            } else {
                                fieldOutput.append(" (primitive)");
                            }
                        } else {
                            fieldOutput.append(" (not accessible)");
                        }

                        logger.log(Level.INFO, fieldOutput.toString());
                    }
                } else {
                    logger.log(Level.INFO, "(no fields)");
                }
            } else{
                //logger.log(Level.INFO, "(uninteresting)"); // too uninteresting to even log!
            }

            //logger.log(Level.INFO, o.getClass().getName()+"@"+System.identityHashCode(o)+" visited, size "+size);
        }
    }

    public HelloWorldFlowView() {
        addClassName("hello-world-flow-view");
        name = new TextField("Your name");
        Button sayHello = new Button("Say hello");
        TreeGrid treeGrid = new TreeGrid<>();
        add(name, sayHello, treeGrid);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue()+"! Session size is: "+sizeOf.deepSizeOf(loggingVisitorListener, VaadinSession.getCurrent()));
            sizes.clear();
        });
    }


}
