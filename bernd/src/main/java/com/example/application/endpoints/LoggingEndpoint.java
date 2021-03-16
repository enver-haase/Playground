package com.example.application.endpoints;

import com.vaadin.flow.server.connect.Endpoint;
import lombok.extern.slf4j.Slf4j;

@Endpoint
@Slf4j
public class LoggingEndpoint {

    public String sayHello(String name){
        return "Hello " + name;
    }

    public void log(String message){
        log.error(message);
        System.err.println(message);
    }

}
