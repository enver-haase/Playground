package com.example.application.endpoints;

import com.vaadin.flow.server.connect.Endpoint;
import com.vaadin.flow.server.connect.auth.AnonymousAllowed;
import lombok.extern.slf4j.Slf4j;

@Endpoint
@Slf4j
public class LoggingEndpoint {

    public String sayHello(String name){
        return "Hello " + name;
    }

    @AnonymousAllowed
    public void log(String message){
        log.debug(message);
    }

}
