package com.nowackdynamics.serv.framework.request.external.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdatePinRequest extends UserRequest {

    @NotNull
    @Pattern(regexp = "^\\d{6}$", message = "Invalid PIN format")
    private String currentPin;

    @Size(min = 60, max = 60, message = "Invalid PIN hash length")
    @Pattern(
            regexp = "^\\$2[ayb]\\$\\d{2}\\$[./A-Za-z0-9]{53}$",
            message = "Invalid PIN hash"
    )
    private String newPin;

    @NotNull
    private String newSalt;
}
