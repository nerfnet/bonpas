package net.insidebits.bonpas.framework.sync;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.insidebits.bonpas.framework.request.ServRequest;

/**
 * A synchronization request. 
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SyncRequest extends ServRequest {

    @NotBlank(message = "Data can't be empty")
    private String data;

    @NotBlank(message = "Invalid checksum")
    @Pattern(regexp = "[a-fA-F0-9]{32}", message = "Invalid MD5 hash")
    private String checksum;
}
