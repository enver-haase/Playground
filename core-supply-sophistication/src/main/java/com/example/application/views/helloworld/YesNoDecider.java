package com.example.application.views.helloworld;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.server.Command;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class YesNoDecider {
    abstract static class MyCommand implements Command {
        volatile boolean done;
    }

    // to be called from within a UI's thread
    public static void yesNo(String question, Consumer<Boolean> consumer) {

        Dialog dialog = new Dialog();

        CompletableFuture<Boolean> future = new CompletableFuture<>();
        dialog.add(new Span(question));
        dialog.add(new Button("yes", e -> future.complete(true)));
        dialog.add(new Button("no", e -> future.complete(false)));
        dialog.setModal(true);
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);
        dialog.open();

        // Command will be run in the UI thread eventually
        final MyCommand command = new MyCommand() {
            @Override
            public void execute() {
                if (future.isDone()) {
                    try {
                        consumer.accept(future.get());
                        done = true;
                    } catch (InterruptedException | ExecutionException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };

        UI ui = UI.getCurrent(); // in the UI thread, get the UI instance
        new Thread(() -> { // worker thread trying to let the UI thread run the command as soon as the required user-input boolean if there
            UI.setCurrent(ui); // in case the command uses 'UI.getCurrent()' assign the parent thread's UI to this new child thread.
            while (!command.done) { // repeat until it is finally done.
                try {
                    ui.access(command).get(); // and use the UI instance in a child thread, block this thread until the UI thread tried another time.
                    // network usage: make sure that there is no polling set so that we only piggy-back upon the client-server communications that take place after some user action such as a click.
                } catch (InterruptedException | ExecutionException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

        // functional identity mapping with a little side effect
        future.thenApply(value -> {
            dialog.close();
            return value;
        });
    }
}
