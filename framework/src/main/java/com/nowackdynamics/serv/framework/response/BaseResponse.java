package com.nowackdynamics.serv.framework.response;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class BaseResponse {

    private ResponseType responseType;

    private int httpCode, ndsCode;

    @Nullable
    private String data;
}
