package com.nowackdynamics.serv.framework.request.external.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdatePinRequest extends UserRequest {

    @NotNull
    private String currentPin;

    @NotNull
    private String newPin;
}
