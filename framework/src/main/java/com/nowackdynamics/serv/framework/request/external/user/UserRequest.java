package com.nowackdynamics.serv.framework.request.external.user;

import com.nowackdynamics.serv.framework.request.BaseRequest;
import com.nowackdynamics.serv.framework.request.Source;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserRequest extends BaseRequest {

    @NotNull
    private UUID userId;

    public UserRequest() {
        setSource(Source.USER);
    }
}
