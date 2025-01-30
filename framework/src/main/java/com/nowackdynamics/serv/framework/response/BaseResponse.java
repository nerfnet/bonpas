package com.nowackdynamics.serv.framework.response;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class BaseResponse {

    private int httpCode;

    @Nullable
    private String data;
}
