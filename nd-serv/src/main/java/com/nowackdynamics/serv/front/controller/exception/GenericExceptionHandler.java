package com.nowackdynamics.serv.front.controller.exception;

import com.nowackdynamics.serv.framework.response.ErrorCodes;
import com.nowackdynamics.serv.framework.response.external.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ErrorResponse> handleRestException(Exception ex, WebRequest request) {
        return ErrorResponse.create("Unknown error", ErrorCodes.UNKNOWN_GENERIC);
    }
}
