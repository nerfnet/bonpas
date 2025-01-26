package com.nowackdynamics.serv.framework.response.external.user;

import com.nowackdynamics.serv.framework.response.external.SuccessResponse;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class GenericSuccessResponse extends SuccessResponse {

    @NotNull
    private UUID id;

    public static ResponseEntity<GenericSuccessResponse> create(UUID id) {
        GenericSuccessResponse genericSuccessResponse = new GenericSuccessResponse();
        genericSuccessResponse.setId(id);
        return ResponseEntity
                .status(genericSuccessResponse.getHttpCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(genericSuccessResponse);
    }
}
