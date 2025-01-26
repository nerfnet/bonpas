package com.nowackdynamics.serv.front;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableAsync
public class NDServ {

    private static ConfigurableApplicationContext appContext;

    public static void main(String[] args) {
        appContext = new SpringApplicationBuilder(NDServ.class).web(WebApplicationType.SERVLET).run(args);
    }
}

