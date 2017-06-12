package com.infraleap.springcourse;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.util.CurrentInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainMenuConfiguration {

    @Bean
    @UIScope
    protected MainMenu configureMainMenu(){
        VaadinRequest req = CurrentInstance.get(VaadinRequest.class);

        String header = req.getHeader("user-agent");
        if (header.contains("Mobile")){ // TODO: capital M?
            return new MobileMainMenuBean();
        }
        else{
            return new MainMenuBean();
        }
    }
}
