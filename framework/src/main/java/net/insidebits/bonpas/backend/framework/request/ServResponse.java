package net.insidebits.bonpas.framework.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * A standard response from the Serv API endpoint.
 */
@Data
public class ServResponse {

    // Constants
    public static ResponseEntity<ServResponse> INVALID_CHECKSUM = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ServResponse(100, "Invalid checksum. Receipt may be corrupt or altered prior or during transmission."));
    public static ResponseEntity<ServResponse> SYNC_ACCEPTED = ResponseEntity.status(HttpStatus.ACCEPTED).body(new ServResponse(1  , "Synchronization accepted."));

    @NotBlank(message = "Illegal Serv code")
    private final int servCode;

    @NotBlank(message = "Empty response message")
    private final String message;
}
