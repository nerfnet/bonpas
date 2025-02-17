package com.nowackdynamics.serv.framework.request.external.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class VerifyEmailRequest extends UserRequest {

    @NotNull
    @Pattern(regexp = "^\\d{6}$", message = "Invalid verification code format")
    private String verificationCode;
}
