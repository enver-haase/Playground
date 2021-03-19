package com.example.application.views.layouting;

import com.example.application.data.entity.SampleAddress;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration()
public class SampleAddressConfig {

    @Bean
    @VaadinSessionScope
    public SampleAddress sessionScopedAddress(){
        return new SampleAddress();
    }

}
