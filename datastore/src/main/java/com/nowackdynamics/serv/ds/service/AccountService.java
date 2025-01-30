package com.nowackdynamics.serv.ds.service;

import com.nowackdynamics.serv.ds.entity.Account;
import com.nowackdynamics.serv.ds.repository.AccountRepository;
import com.nowackdynamics.serv.framework.response.BaseResponse;
import com.nowackdynamics.serv.framework.response.external.ErrorResponse;
import com.nowackdynamics.serv.framework.response.external.user.GenericSuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    /**
     * Handles the creation of a new user {@link Account}.
     *
     * @param userId The UUID of the user.
     * @param email  The email to be assigned to the account.
     * @param pin    The PIN to be assigned to the account.
     * @return HTTP response containing user id if success.
     */
    public ResponseEntity<? extends BaseResponse> handleCreate(UUID userId, String email, String pin) {
        Optional<Account> optional = repository.findById(userId);

        if (optional.isPresent()) {
            return ErrorResponse.create("Account already exists");
        } else {
            if(repository.findByEmail(email.toLowerCase()).isPresent()) {
                return ErrorResponse.create("Email already in use");
            }
        }

        Account account = new Account();
        account.setId(userId);
        account.setEmail(email.toLowerCase());
        account.setPin(pin);

        repository.save(account);

        return GenericSuccessResponse.create(userId);
    }

    /**
     * Handles the update of a user's PIN.
     *
     * @param userId     The UUID of the user to change the PIN of.
     * @param currentPin The current PIN of the user, used for verification.
     * @param newPin     The new PIN to set.
     * @return HTTP response containing user id if success.
     */
    public ResponseEntity<? extends BaseResponse> handleUpdatePin(UUID userId, String currentPin, String newPin) {
        Optional<Account> optional = repository.findById(userId);
        if (optional.isPresent()) {
            Account account = optional.get();
            if (account.getPin().equals(currentPin)) {
                account.setPin(newPin);
            } else {
                return ErrorResponse.create("Security check failed");
            }
            repository.save(account);
            return GenericSuccessResponse.create(userId);
        }
        return ErrorResponse.create("Account not registered");
    }

    /**
     * Handles the update of a user's email.
     *
     * @param userId     The UUID of the user to change the PIN of.
     * @param currentPin The current PIN of the user, used for verification.
     * @param newEmail   The new email to set.
     * @return HTTP response containing user id if success.
     */
    public ResponseEntity<? extends BaseResponse> handleUpdateEmail(UUID userId, String currentPin, String newEmail) {
        Optional<Account> optional = repository.findById(userId);
        if (optional.isPresent()) {
            Account account = optional.get();
            if (account.getPin().equals(currentPin)) {
                if (account.getEmail().equals(newEmail)) {
                    return ErrorResponse.create("New email is the same");
                }
                account.setEmail(newEmail);
            } else {
                return ErrorResponse.create("Security check failed");
            }
            repository.save(account);
            return GenericSuccessResponse.create(userId);
        }
        return ErrorResponse.create("Account not registered");
    }
}
