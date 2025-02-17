package com.nowackdynamics.serv.framework.response.external.user;

import com.nowackdynamics.serv.framework.response.ResponseType;
import com.nowackdynamics.serv.framework.response.external.SuccessResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class PinUpdatedResponse extends SuccessResponse {

    @NotNull
    private UUID id;

    @Size(min = 60, max = 60, message = "Invalid PIN hash length")
    @Pattern(
            regexp = "^\\$2[ayb]\\$\\d{2}\\$[./A-Za-z0-9]{53}$",
            message = "Invalid PIN hash"
    )
    private String pin;

    public static ResponseEntity<PinUpdatedResponse> create(UUID id, String pin) {
        PinUpdatedResponse genericSuccessResponse = new PinUpdatedResponse();
        genericSuccessResponse.setResponseType(ResponseType.PIN_UPDATED);
        genericSuccessResponse.setId(id);
        genericSuccessResponse.setPin(pin);
        return ResponseEntity
                .status(genericSuccessResponse.getHttpCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(genericSuccessResponse);
    }
}
