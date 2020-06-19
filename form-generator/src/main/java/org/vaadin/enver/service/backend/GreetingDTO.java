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
    String hello;
    boolean helloEditable;
    boolean helloMandatory;
    boolean helloPermission;

    Instant time;
    boolean timeEditable = false;
    boolean timeMandatory = false;
    boolean timePermission = true;

    String secret;
    boolean secretEditable;
    boolean secretMandatory;
    boolean secretPermission;

    public void setTime(Instant time){
        this.time = time;
        log.warn("NEW TIME TO BE SAVED TO DATABASE: "+time);
    }

    public void setHello(String hello){
        this.hello = hello;
        log.warn("NEW HELLO TO BE SAVED TO DATABASE: "+hello);
    }
}
