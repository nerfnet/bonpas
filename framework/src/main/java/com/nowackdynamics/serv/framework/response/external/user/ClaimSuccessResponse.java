package com.nowackdynamics.serv.framework.response.external.user;

import com.nowackdynamics.serv.framework.response.ResponseType;
import com.nowackdynamics.serv.framework.response.external.SuccessResponse;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClaimSuccessResponse extends SuccessResponse {

    @NotNull
    private UUID receiptId;

    public static ResponseEntity<ClaimSuccessResponse> create(UUID receiptId, String data) {
        ClaimSuccessResponse claimSuccessResponse = new ClaimSuccessResponse();
        claimSuccessResponse.setResponseType(ResponseType.CLAIM_SUCCESS);
        claimSuccessResponse.setData(data);
        claimSuccessResponse.setReceiptId(receiptId);
        return ResponseEntity
                .status(claimSuccessResponse.getHttpCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(claimSuccessResponse);
    }
}
