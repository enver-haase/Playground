package org.vaadin.enver.service.backend;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class GreetService implements Serializable {

    /**
     * Note that the 'secret' is not set at all because the Back-End decided that
     * depending on the session of the user currently logged in, 'secret' will not
     * even be set so it is all set to Java default values (null / false).
     *
     * @return a greeting from the 'database' with a possibly mandatory field
     * entirely un-set because of simulated security restrictions.
     */
    public GreetingDTO getGreeting(){

        GreetingDTO bean = new GreetingDTO();

        bean.hello = "Hallo Thoralf!";
        bean.helloEditable = true;
        bean.helloMandatory = true;
        bean.helloPermission = true;

        long myBirthday = LocalDate.of(1973, 3, 10).toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC);
        bean.time = Instant.ofEpochSecond(myBirthday);
        bean.setTimeEditable(false);
        bean.setTimeMandatory(true);
        bean.setTimePermission(true);

        return bean;
    }


}
