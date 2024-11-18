package net.insidebits.bonpas.backend.serv2;

import net.insidebits.bonpas.backend.serv2.crypto.Crypto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Serv is a tiny endpoint that handles sync & fetch requests
 * from the Bonpas app.
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Serv {

    private static ConfigurableApplicationContext appContext;

    public static void main(String[] args) {
        if (!Crypto.verifyIntegrity()) {
            return;
        }

        appContext = SpringApplication.run(Serv.class, args);
    }
}
