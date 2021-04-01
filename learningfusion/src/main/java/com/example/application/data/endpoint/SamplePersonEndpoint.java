package com.example.application.data.endpoint;

import com.example.application.data.CrudEndpoint;
import com.example.application.data.entity.SamplePerson;
import com.example.application.data.service.SamplePersonService;
import com.vaadin.flow.server.connect.Endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import javax.annotation.Nullable;

@Endpoint
public class SamplePersonEndpoint extends CrudEndpoint<SamplePerson, Integer> {

    private SamplePersonService service;

    public SamplePersonEndpoint(@Autowired SamplePersonService service) {
        this.service = service;
    }

    @Override
    protected SamplePersonService getService() {
        return service;
    }

}
