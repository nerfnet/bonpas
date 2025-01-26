package com.nowackdynamics.serv.front.controller;

import com.nowackdynamics.serv.framework.request.external.user.CreateAccountRequest;
import com.nowackdynamics.serv.framework.request.external.user.UpdateEmailRequest;
import com.nowackdynamics.serv.framework.request.external.user.UpdatePinRequest;
import com.nowackdynamics.serv.framework.response.BaseResponse;
import com.nowackdynamics.serv.framework.response.external.ErrorResponse;
import com.nowackdynamics.serv.front.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;


@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private Exchange exchange;

    @PostMapping("/create")
    public ResponseEntity<? extends BaseResponse> create(@Validated @RequestBody CreateAccountRequest createRequest) {
        ResponseEntity<? extends BaseResponse> response;

        try {
            response = exchange.exchangeSync("/api/account/create", createRequest);
        } catch (RestClientException e) {
            return ErrorResponse.create("CAR Exchange error");
        }

        return response;
    }

    @PostMapping("/updatepin")
    public ResponseEntity<?> updatePin(@Validated @RequestBody UpdatePinRequest updatePinRequest) {
        ResponseEntity<? extends BaseResponse> response;

        try {
            response = exchange.exchangeSync("/api/account/updatepin", updatePinRequest);
        } catch (RestClientException e) {
            return ErrorResponse.create("UPR Exchange error");
        }

        return response;
    }

    @PostMapping("/updateemail")
    public ResponseEntity<?> updateEmail(@Validated @RequestBody UpdateEmailRequest updateEmailRequest) {
        ResponseEntity<? extends BaseResponse> response;

        try {
            response = exchange.exchangeSync("/api/account/updateemail", updateEmailRequest);
        } catch (RestClientException e) {
            return ErrorResponse.create("UEQ Exchange error");
        }

        return response;
    }
}
