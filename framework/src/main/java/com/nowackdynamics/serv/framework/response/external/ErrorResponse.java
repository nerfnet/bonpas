package com.nowackdynamics.serv.framework.response.external;

import com.nowackdynamics.serv.framework.response.BaseResponse;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorResponse extends BaseResponse {

    @NotNull
    private String reason;

    public static ResponseEntity<ErrorResponse> create(String reason, int errorCode) {
        ErrorResponse response = new ErrorResponse();
        response.setHttpCode(400);
        response.setReason(reason);
        response.setNdsCode(errorCode);
        return ResponseEntity
                .status(response.getHttpCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
