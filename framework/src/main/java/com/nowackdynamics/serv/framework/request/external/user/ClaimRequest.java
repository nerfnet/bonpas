package com.nowackdynamics.serv.framework.request.external.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClaimRequest extends UserRequest {

    @NotNull
    private UUID receiptId;
}
