package com.nowackdynamics.serv.front.controller;

import com.nowackdynamics.serv.framework.request.external.user.CreateAccountRequest;
import com.nowackdynamics.serv.framework.request.external.user.UpdateEmailRequest;
import com.nowackdynamics.serv.framework.request.external.user.UpdatePinRequest;
import com.nowackdynamics.serv.framework.response.BaseResponse;
import com.nowackdynamics.serv.front.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private Exchange exchange;

    @PostMapping("/create")
    public ResponseEntity<? extends BaseResponse> create(@Validated @RequestBody CreateAccountRequest createRequest) {
        ResponseEntity<? extends BaseResponse> response;
        response = exchange.exchangeSync("/api/account/create", createRequest);

        return response;
    }

    @PostMapping("/updatepin")
    public ResponseEntity<?> updatePin(@Validated @RequestBody UpdatePinRequest updatePinRequest) {
        ResponseEntity<? extends BaseResponse> response;


        response = exchange.exchangeSync("/api/account/updatepin", updatePinRequest);


        return response;
    }

    @PostMapping("/updateemail")
    public ResponseEntity<?> updateEmail(@Validated @RequestBody UpdateEmailRequest updateEmailRequest) {
        ResponseEntity<? extends BaseResponse> response;


        response = exchange.exchangeSync("/api/account/updateemail", updateEmailRequest);


        return response;
    }
}
