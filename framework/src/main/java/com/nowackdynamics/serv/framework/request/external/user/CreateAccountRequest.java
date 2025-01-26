package com.nowackdynamics.serv.framework.request.external.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateAccountRequest extends UserRequest {

    @NotNull
    private String email;

    @NotNull
    private String pin;
}
