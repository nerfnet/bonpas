package net.insidebits.bonpas.framework.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * A standard request to any Serv API endpoint.
 * Each request must contain an USER ID.
 */
@Data
public class ServRequest {

    @NotBlank(message = "Invalid user ID")
    private String userId;
}
