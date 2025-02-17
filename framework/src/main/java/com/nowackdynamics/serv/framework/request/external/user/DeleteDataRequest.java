package com.nowackdynamics.serv.framework.request.external.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeleteDataRequest extends UserRequest {

    @NotNull
    private boolean fullErasure;

    @NotNull
    @Pattern(regexp = "^\\d{6}$", message = "Invalid PIN format")
    private String pin;
}
