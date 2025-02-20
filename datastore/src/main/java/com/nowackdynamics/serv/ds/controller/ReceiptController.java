package com.nowackdynamics.serv.ds.controller;

import com.nowackdynamics.serv.ds.Secure;
import com.nowackdynamics.serv.ds.service.ReceiptService;
import com.nowackdynamics.serv.framework.request.external.terminal.NewReceiptRequest;
import com.nowackdynamics.serv.framework.request.external.user.ClaimRequest;
import com.nowackdynamics.serv.framework.response.ErrorCodes;
import com.nowackdynamics.serv.framework.response.external.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/receipt")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private Secure secure;

    @PostMapping("/new")
    public ResponseEntity<?> createNew(
            @Validated @RequestBody NewReceiptRequest newReceiptRequest,
            @RequestHeader(value = "Authorization") String securityKey) {
        if (!secure.checkKey(securityKey)) {
            return ErrorResponse.create("Secure check error", ErrorCodes.SECURITY_FAILURE);
        }

        UUID receiptId = newReceiptRequest.getReceiptId();
        String receiptData = newReceiptRequest.getReceiptData();
        Map<String, Object> analyticsInfo = newReceiptRequest.getAnalyticsInfo();
        return receiptService.handleNew(
                receiptId,
                receiptData,
                analyticsInfo
        );
    }

    @PostMapping("/claim")
    public ResponseEntity<?> claim(
            @Validated @RequestBody ClaimRequest claimRequest,
            @RequestHeader(value = "Authorization") String securityKey) {
        if (!secure.checkKey(securityKey)) {
            return ErrorResponse.create("Secure check error", ErrorCodes.SECURITY_FAILURE);
        }

        UUID receiptId = claimRequest.getReceiptId();
        UUID userId = claimRequest.getUserId();
        return receiptService.handleClaim(
                receiptId,
                userId
        );
    }
}
