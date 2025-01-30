package com.nowackdynamics.serv.framework.request.external.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateEmailRequest extends UserRequest {

    @NotNull
    @Pattern(regexp = "^\\d{6}$", message = "Invalid PIN format")
    private String pin;

    @NotNull
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String newEmail;
}
