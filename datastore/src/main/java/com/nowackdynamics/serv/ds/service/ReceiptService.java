package com.nowackdynamics.serv.ds.service;

import com.nowackdynamics.serv.ds.entity.Receipt;
import com.nowackdynamics.serv.ds.repository.ReceiptRepository;
import com.nowackdynamics.serv.framework.response.BaseResponse;
import com.nowackdynamics.serv.framework.response.external.ErrorResponse;
import com.nowackdynamics.serv.framework.response.external.user.GenericSuccessResponse;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReceiptService {

    @Autowired
    private ReceiptRepository repository;

    /**
     * Handles the creation of a new {@link Receipt}
     *
     * @param receiptId     The UUID of the receipt.
     * @param receiptData   The Base64 encoded data of the receipt.
     * @param analyticsInfo Analytics info.
     * @return HTTP response containing receipt id if success.
     */
    public ResponseEntity<? extends BaseResponse> handleNew(UUID receiptId, String receiptData, @Nullable ArrayList<String> analyticsInfo) {
        if (repository.findById(receiptId).isPresent()) {
            return ErrorResponse.create("Receipt with specified ID already exists");
        }

        Receipt receipt = new Receipt();
        receipt.setReceiptId(receiptId);
        receipt.setData(receiptData);
        receipt.setAnalyticsInfo(Objects.requireNonNullElseGet(analyticsInfo, ArrayList::new));

        repository.save(receipt);
        return GenericSuccessResponse.create(receiptId);
    }

    /**
     * Handles the claiming of a {@link Receipt}.
     *
     * @param receiptId The UUID of the receipt to claim.
     * @param userId    The UUID of the user claiming the receipt.
     * @return HTTP response containing the receipt id if success.
     */
    public ResponseEntity<? extends BaseResponse> handleClaim(UUID receiptId, UUID userId) {
        Optional<Receipt> optional = repository.findById(receiptId);
        if (optional.isPresent()) {
            Receipt receipt = optional.get();
            if (receipt.getClaimedBy() != null) {
                return ErrorResponse.create("Receipt already claimed");
            }
            receipt.setClaimedBy(userId);
            repository.save(receipt);
            return GenericSuccessResponse.create(receiptId);
        }
        return ErrorResponse.create("Receipt does not exist");
    }
}
