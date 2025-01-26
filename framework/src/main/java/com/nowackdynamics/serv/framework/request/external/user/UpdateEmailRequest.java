package com.nowackdynamics.serv.framework.request.external.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateEmailRequest extends UserRequest {

    @NotNull
    private String pin;

    @NotNull
    private String newEmail;
}
