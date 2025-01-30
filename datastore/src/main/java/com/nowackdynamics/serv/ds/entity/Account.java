package com.nowackdynamics.serv.ds.entity;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "accounts")
@Data
public class Account {

    @Id
    private UUID id;

    private String email;

    private String pin;
}
