package com.nowackdynamics.serv.framework.request.external.terminal;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class NewReceiptRequest extends TerminalRequest {

    @NotNull
    private UUID receiptId;

    @NotNull
    private String receiptData;
}
