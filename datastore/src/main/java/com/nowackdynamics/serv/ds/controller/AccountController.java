package com.nowackdynamics.serv.ds.controller;

import com.nowackdynamics.serv.ds.Secure;
import com.nowackdynamics.serv.ds.service.AccountService;
import com.nowackdynamics.serv.framework.request.external.user.*;
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
        String salt = createRequest.getSalt();
        return accountService.handleCreate(
                userId,
                email,
                pin,
                salt
        );
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(
            @Validated @RequestBody DeleteDataRequest deleteRequest,
            @RequestHeader(value = "Authorization") String securityKey) {
        if (!secure.checkKey(securityKey)) {
            return ErrorResponse.create("Secure check error", ErrorCodes.SECURITY_FAILURE);
        }
        UUID userId = deleteRequest.getUserId();
        String pin = deleteRequest.getPin();
        boolean fullErasure = deleteRequest.isFullErasure();
        return accountService.handleDelete(
                userId,
                pin,
                fullErasure
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
        String newSalt = updatePinRequest.getNewSalt();
        return accountService.handleUpdatePin(
                userId,
                currentPin,
                newPin,
                newSalt
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

    @PostMapping("/verification")
    public ResponseEntity<?> verifyEmail(
            @Validated @RequestBody VerifyEmailRequest verifyEmailRequest,
            @RequestHeader(value = "Authorization") String securityKey) {
        if (!secure.checkKey(securityKey)) {
            return ErrorResponse.create("Secure check error", ErrorCodes.SECURITY_FAILURE);
        }
        UUID userId = verifyEmailRequest.getUserId();
        String code = verifyEmailRequest.getVerificationCode();
        return accountService.handleVerifyEmail(
                userId,
                code
        );
    }

    @PostMapping("/verification/mark")
    public ResponseEntity<?> markEmailForVerification(
            @Validated @RequestBody MarkVerificationRequest markVerificationRequest,
            @RequestHeader(value = "Authorization") String securityKey) {
        if (!secure.checkKey(securityKey)) {
            return ErrorResponse.create("Secure check error", ErrorCodes.SECURITY_FAILURE);
        }
        UUID userId = markVerificationRequest.getUserId();
        return accountService.handleMarkEmailPending(
                userId
        );
    }
}
