package com.nowackdynamics.serv.ds.entity;

import java.time.Duration;
import java.time.LocalDateTime;

public class VerificationToken {

    public String code;

    public LocalDateTime generationTime;

    public boolean expired() {
        Duration difference = Duration.between(generationTime, LocalDateTime.now());
        return difference.toMinutes() >= 5;
    }
}
