package com.nowackdynamics.serv.ds;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Datastore {

    private static ConfigurableApplicationContext appContext;

    public static void main(String[] args) {
        appContext = new SpringApplicationBuilder(Datastore.class).web(WebApplicationType.SERVLET).run(args);
    }
}
