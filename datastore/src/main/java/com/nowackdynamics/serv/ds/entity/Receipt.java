package com.nowackdynamics.serv.ds.entity;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.UUID;

@Document(collection = "receipts")
@Data
public class Receipt {

    @Id
    private UUID receiptId;

    private UUID claimedBy;

    private String data;

    private ArrayList<String> analyticsInfo;
}
