package com.nowackdynamics.serv.front.controller;

import com.nowackdynamics.serv.framework.request.external.terminal.NewReceiptRequest;
import com.nowackdynamics.serv.framework.request.external.user.ClaimRequest;
import com.nowackdynamics.serv.framework.response.BaseResponse;
import com.nowackdynamics.serv.framework.response.external.user.GenericSuccessResponse;
import com.nowackdynamics.serv.front.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/receipt")
public class ReceiptController {

    @Autowired
    private Exchange exchange;

    @PostMapping("/new")
    public ResponseEntity<?> createNew(@Validated @RequestBody NewReceiptRequest newReceiptRequest) {
        // async
        exchange.exchangeAsync("/api/receipt/new", newReceiptRequest);
        // instant response since the RPD Terminal needs to serve the receipt id as quick as possible.
        return GenericSuccessResponse.create(newReceiptRequest.getReceiptId());
    }

    @PostMapping("/claim")
    public ResponseEntity<?> claim(@Validated @RequestBody ClaimRequest claimRequest) {
        ResponseEntity<? extends BaseResponse> response;
        response = exchange.exchangeSync("/api/receipt/claim", claimRequest);
        return  response;
    }
}
