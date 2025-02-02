package com.nowackdynamics.serv.ds.controller;

import com.nowackdynamics.serv.ds.Secure;
import com.nowackdynamics.serv.ds.service.AccountService;
import com.nowackdynamics.serv.framework.request.external.user.CreateAccountRequest;
import com.nowackdynamics.serv.framework.request.external.user.UpdateEmailRequest;
import com.nowackdynamics.serv.framework.request.external.user.UpdatePinRequest;
import com.nowackdynamics.serv.framework.response.ErrorCodes;
import com.nowackdynamics.serv.framework.response.external.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private Secure secure;

    @PostMapping("/create")
    public ResponseEntity<?> create(
            @Validated @RequestBody CreateAccountRequest createRequest,
            @RequestHeader(value = "Authorization") String securityKey) {
        if (!secure.checkKey(securityKey)) {
            return ErrorResponse.create("Secure check error", ErrorCodes.SECURITY_FAILURE);
        }
        UUID userId = createRequest.getUserId();
        String email = createRequest.getEmail();
        String pin = createRequest.getPin();
        return accountService.handleCreate(
                userId,
                email,
                pin
        );
    }

    @PostMapping("/updatepin")
    public ResponseEntity<?> updatePin(@Validated @RequestBody UpdatePinRequest updatePinRequest,
                                       @RequestHeader(value = "Authorization") String securityKey) {
        if (!secure.checkKey(securityKey)) {
            return ErrorResponse.create("Secure check error", ErrorCodes.SECURITY_FAILURE);
        }
        UUID userId = updatePinRequest.getUserId();
        String currentPin = updatePinRequest.getCurrentPin();
        String newPin = updatePinRequest.getNewPin();
        return accountService.handleUpdatePin(
                userId,
                currentPin,
                newPin
        );
    }

    @PostMapping("/updateemail")
    public ResponseEntity<?> updateEmail(@Validated @RequestBody UpdateEmailRequest updateEmailRequest,
                                         @RequestHeader(value = "Authorization") String securityKey) {
        if (!secure.checkKey(securityKey)) {
            return ErrorResponse.create("Secure check error", ErrorCodes.SECURITY_FAILURE);
        }
        UUID userId = updateEmailRequest.getUserId();
        String pin = updateEmailRequest.getPin();
        String newEmail = updateEmailRequest.getNewEmail();
        return accountService.handleUpdateEmail(
                userId,
                pin,
                newEmail
        );
    }
}
