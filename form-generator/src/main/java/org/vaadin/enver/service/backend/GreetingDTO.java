package org.vaadin.enver.service.backend;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

/**
 * This is an example Data Transfer Object as it could be used by a low-level
 * REST service.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Slf4j
public class GreetingDTO extends DTO{
    private String hello;
    private boolean helloEditable;
    private boolean helloMandatory;
    private boolean helloPermission;

    private Instant time;
    private boolean timeEditable = false;
    private boolean timeMandatory = false;
    private boolean timePermission = true;

    private String secret;
    private boolean secretEditable;
    private boolean secretMandatory;
    private boolean secretPermission;


    public void setTime(Instant time){
        this.time = time;
        log.warn("NEW TIME TO BE SAVED TO DATABASE: "+time);
    }

    public void setHello(String hello){
        this.hello = hello;
        log.warn("NEW HELLO TO BE SAVED TO DATABASE: "+hello);
    }
}
