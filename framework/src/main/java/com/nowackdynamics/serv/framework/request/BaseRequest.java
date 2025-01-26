package com.nowackdynamics.serv.framework.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BaseRequest {

    @NotNull
    private Source source;
}
