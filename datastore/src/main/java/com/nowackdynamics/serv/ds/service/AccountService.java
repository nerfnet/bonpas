package com.nowackdynamics.serv.ds.service;

import com.nowackdynamics.serv.ds.entity.Account;
import com.nowackdynamics.serv.ds.entity.VerificationToken;
import com.nowackdynamics.serv.ds.repository.AccountRepository;
import com.nowackdynamics.serv.ds.repository.ReceiptRepository;
import com.nowackdynamics.serv.framework.response.BaseResponse;
import com.nowackdynamics.serv.framework.response.ErrorCodes;
import com.nowackdynamics.serv.framework.response.external.ErrorResponse;
import com.nowackdynamics.serv.framework.response.external.user.GenericSuccessResponse;
import com.nowackdynamics.serv.framework.response.external.user.PinUpdatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    private final ConcurrentHashMap<UUID, VerificationToken> pendingEmailVerifications = new ConcurrentHashMap<>();

    @Autowired
    private AccountRepository repository;

    @Autowired
    private ReceiptRepository receiptRepository;

    /**
     * Marks a user's email as pending for verification.
     *
     * @param userId The ID of the user.
     * @return HTTP response containing user id if success.
     */
    public ResponseEntity<? extends BaseResponse> handleMarkEmailPending(UUID userId) {
        Optional<Account> optional = repository.findById(userId);

        if (optional.isPresent()) {
            Account account = optional.get();
            if (account.isEmailVerified()) {
                return ErrorResponse.create("Email already verified", ErrorCodes.EMAIL_VERIFIED);
            }
            VerificationToken verificationToken = new VerificationToken();
            verificationToken.generationTime = LocalDateTime.now();
            verificationToken.code = String.format("%06d", ThreadLocalRandom.current().nextInt(999999));
            pendingEmailVerifications.put(userId, verificationToken);
            return GenericSuccessResponse.create(userId);
        }
        return ErrorResponse.create("Account does not exist", ErrorCodes.ACCOUNT_INVALID);
    }

    /**
     * Handles the verification of a user's email address.
     *
     * @param userId           The ID of the user to verify the email of.
     * @param verificationCode The verification code sent to the user.
     * @return HTTP response containing user id if success.
     */
    public ResponseEntity<? extends BaseResponse> handleVerifyEmail(UUID userId, String verificationCode) {
        Optional<Account> optional = repository.findById(userId);

        if (optional.isPresent()) {
            Account account = optional.get();
            if (account.isEmailVerified()) {
                return ErrorResponse.create("Email already verified", ErrorCodes.EMAIL_VERIFIED);
            }

            if (!pendingEmailVerifications.containsKey(userId)) {
                return ErrorResponse.create("Email verification not pending", ErrorCodes.UNKNOWN_GENERIC);
            }

            VerificationToken token = pendingEmailVerifications.get(userId);
            if (token.generationTime.isBefore(LocalDateTime.now())) {
                return ErrorResponse.create("Email verification code expired", ErrorCodes.EMAIL_VERIFICATION_EXPIRED);
            }

            account.setEmailVerified(true);
            repository.save(account);
            pendingEmailVerifications.remove(userId);

            return GenericSuccessResponse.create(userId);
        }
        return ErrorResponse.create("Account does not exist", ErrorCodes.ACCOUNT_INVALID);
    }


    /**
     * Handles the creation of a new user {@link Account}.
     *
     * @param userId The UUID of the user.
     * @param email  The email to be assigned to the account.
     * @param pin    The PIN to be assigned to the account.
     * @return HTTP response containing user id if success.
     */
    public ResponseEntity<? extends BaseResponse> handleCreate(UUID userId, String email, String pin, String salt) {
        Optional<Account> optional = repository.findById(userId);

        if (optional.isPresent()) {
            return ErrorResponse.create("Account already exists", ErrorCodes.ACCOUNT_EXISTS);
        } else {
            if (repository.existsByEmail(email.toLowerCase())) {
                return ErrorResponse.create("Email already in use", ErrorCodes.EMAIL_USED);
            }
        }

        Account account = new Account();
        account.setId(userId);
        account.setEmail(email.toLowerCase());
        account.setPin(pin);

        repository.save(account);

        return PinUpdatedResponse.create(userId, pin);
    }

    /**
     * Handles the deletion of user data.
     *
     * @param userId    The UUID of the user.
     * @param pin       The PIN of the user.
     * @param fullErase Whether to do a Full Erasure or just an Account Deletion.
     */
    public ResponseEntity<? extends BaseResponse> handleDelete(UUID userId, String pin, boolean fullErase) {
        Optional<Account> accOptional = repository.findById(userId);

        if (accOptional.isEmpty()) {
            return ErrorResponse.create("Account does not exist", ErrorCodes.ACCOUNT_INVALID);
        }

        if (!authenticate(userId, pin)) {
            return ErrorResponse.create("Security check failed", ErrorCodes.SECURITY_FAILURE);
        }

        if (fullErase) {
            receiptRepository.deleteByClaimedBy(userId);
        }

        repository.deleteById(userId);
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
    public ResponseEntity<? extends BaseResponse> handleUpdatePin(UUID userId, String currentPin, String newPin, String newSalt) {
        Optional<Account> optional = repository.findById(userId);
        if (optional.isPresent()) {
            Account account = optional.get();
            if (authenticate(userId, currentPin)) {
                account.setPin(newPin);
            } else {
                return ErrorResponse.create("Security check failed", ErrorCodes.PIN_INVALID);
            }
            repository.save(account);
            return PinUpdatedResponse.create(userId, newPin);
        }
        return ErrorResponse.create("Account not registered", ErrorCodes.ACCOUNT_INVALID);
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
        if (repository.existsByEmail(newEmail)) {
            return ErrorResponse.create("Email already in use", ErrorCodes.EMAIL_USED);
        }

        Optional<Account> optional = repository.findById(userId);
        if (optional.isPresent()) {
            Account account = optional.get();
            if (authenticate(userId, currentPin)) {
                if (account.getEmail().equals(newEmail)) {
                    return ErrorResponse.create("New email is the same", ErrorCodes.EMAIL_SAME);
                }
                account.setEmail(newEmail);
            } else {
                return ErrorResponse.create("Security check failed", ErrorCodes.PIN_INVALID);
            }
            repository.save(account);
            return GenericSuccessResponse.create(userId);
        }
        return ErrorResponse.create("Account not registered", ErrorCodes.ACCOUNT_INVALID);
    }

    private boolean authenticate(UUID userId, String providedPin) {
        Optional<Account> optional = repository.findById(userId);
        if (optional.isPresent()) {
            Account account = optional.get();
            return passwordEncoder.matches(providedPin, account.getPin());
        }
        return false;
    }
}
