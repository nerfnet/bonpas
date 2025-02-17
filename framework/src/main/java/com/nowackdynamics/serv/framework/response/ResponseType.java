package com.nowackdynamics.serv.framework.response;

import com.nowackdynamics.serv.framework.response.external.ErrorResponse;
import com.nowackdynamics.serv.framework.response.external.SuccessResponse;
import com.nowackdynamics.serv.framework.response.external.user.ClaimSuccessResponse;
import com.nowackdynamics.serv.framework.response.external.user.GenericSuccessResponse;
import com.nowackdynamics.serv.framework.response.external.user.PinUpdatedResponse;

public enum ResponseType {

    ERROR(ErrorResponse.class),
    SUCCESS(SuccessResponse.class),
    CLAIM_SUCCESS(ClaimSuccessResponse.class),
    GENERIC_SUCCESS(GenericSuccessResponse.class),
    PIN_UPDATED(PinUpdatedResponse.class);

    private final Class<? extends BaseResponse> responseClass;

    ResponseType(Class<? extends BaseResponse> responseClass) {
        this.responseClass = responseClass;
    }

    public Class<? extends BaseResponse> getResponseClass() {
        return responseClass;
    }
}
