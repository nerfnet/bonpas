package com.nowackdynamics.serv.ds;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Secure {

    @Value("${server.security_token}")
    private String securityKey;

    public boolean checkKey(String input) {
        return input.equals(securityKey);
    }
}
